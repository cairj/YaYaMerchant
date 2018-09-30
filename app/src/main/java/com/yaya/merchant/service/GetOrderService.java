package com.yaya.merchant.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.OrderAction;
import com.yaya.merchant.activity.MainActivity;
import com.yaya.merchant.data.order.OrderNoticeDetail;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.DeviceParamsUtil;
import com.yaya.merchant.util.VoiceUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by 蔡蓉婕 on 2018/9/30.
 */

public class GetOrderService extends Service {

    private static final String TAG = "GetOrderService";
    private boolean mReflectFlg = false;

    private static final int GET_ORDER_COUNT = 100;

    private static final int NOTIFICATION_ID = 1;// 如果id设置为0,会导致不能设置为前台service
    private static final Class<?>[] mSetForegroundSignature = new Class[]{boolean.class};
    private static final Class<?>[] mStartForegroundSignature = new Class[]{int.class, Notification.class};
    private static final Class<?>[] mStopForegroundSignature = new Class[]{boolean.class};

    private NotificationManager mNM;
    private Method mSetForeground;
    private Method mStartForeground;
    private Method mStopForeground;
    private Object[] mSetForegroundArgs = new Object[1];
    private Object[] mStartForegroundArgs = new Object[2];
    private Object[] mStopForegroundArgs = new Object[1];
    private boolean isStart = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("GetOrderService","onCreate");
        mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            mStartForeground = GetOrderService.class.getMethod("startForeground", mStartForegroundSignature);
            mStopForeground = GetOrderService.class.getMethod("stopForeground", mStopForegroundSignature);
        } catch (NoSuchMethodException e) {
            mStartForeground = mStopForeground = null;
        }

        try {
            mSetForeground = getClass().getMethod("setForeground", mSetForegroundSignature);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(
                    "OS doesn't have Service.startForeground OR Service.setForeground!");
        }

        Notification.Builder builder = new Notification.Builder(this);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.mipmap.ic_logo);
        builder.setTicker(getString(R.string.app_name) + "  正在运行");
        builder.setContentTitle(getString(R.string.app_name));

        Notification notification = builder.build();
        startForegroundCompat(NOTIFICATION_ID, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForegroundCompat(NOTIFICATION_ID);
        isStart = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.e("GetOrderService","isStart");
        VoiceUtils.getInstance().speak(GetOrderService.this,"成功");
        isStart = true;
        handler.sendEmptyMessage(GET_ORDER_COUNT);
        return START_STICKY;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isStart){
                return;
            }
            switch (msg.what){
                case GET_ORDER_COUNT:
                    Log.e("GetOrderService","getOrderNotice");
                    OrderAction.getOrderNotice(DeviceParamsUtil.getUdid(GetOrderService.this), new GsonCallback<OrderNoticeDetail>(OrderNoticeDetail.class) {
                        @Override
                        public void onSucceed(JsonResponse<OrderNoticeDetail> response) {
                            List<OrderNoticeDetail> list = response.getDataList();
                            if (!isStart){
                                return;
                            }
                            if (list.size() <= 0){
                                handler.sendEmptyMessageDelayed(GET_ORDER_COUNT,5000);
                            }else {
                                speakContent(list, 0);
                            }
                        }
                    });
                    break;
            }
        }
    };

    private void speakContent(final List<OrderNoticeDetail> list, final int count){
        VoiceUtils.getInstance().speak(GetOrderService.this, list.get(count).getContent(), new VoiceUtils.NormalSynthesizerListener() {
            @Override
            public void onCompleted(SpeechError speechError) {
                if (count >= 0 && count < list.size()-1){
                    speakContent(list,count+1);
                    Log.e("GetOrderService","speakContent");
                }
                if (count == list.size()-1){//最后一条
                    handler.sendEmptyMessageDelayed(GET_ORDER_COUNT,5000);
                }
            }
        });
    }

    void invokeMethod(Method method, Object[] args) {
        try {
            method.invoke(this, args);
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        }
    }

    /**
     * This is a wrapper around the new startForeground method, using the older
     * APIs if it is not available.
     */
    void startForegroundCompat(int id, Notification notification) {
        if (mReflectFlg) {
            // If we have the new startForeground API, then use it.             
            if (mStartForeground != null) {
                mStartForegroundArgs[0] = Integer.valueOf(id);
                mStartForegroundArgs[1] = notification;
                invokeMethod(mStartForeground, mStartForegroundArgs);
                return;
            }
            // Fall back on the old API.
            mSetForegroundArgs[0] = Boolean.TRUE;
            invokeMethod(mSetForeground, mSetForegroundArgs);
            mNM.notify(id, notification);
        } else {
            /* 还可以使用以下方法，当sdk大于等于5时，调用sdk现有的方法startForeground设置前台运行，              
            * 否则调用反射取得的sdk level 5（对应Android 2.0）以下才有的旧方法setForeground设置前台运行
             */
            if (Build.VERSION.SDK_INT >= 5) {
                startForeground(id, notification);
            } else {
                // Fall back on the old API.
                mSetForegroundArgs[0] = Boolean.TRUE;
                invokeMethod(mSetForeground, mSetForegroundArgs);
                mNM.notify(id, notification);
            }
        }
    }

    /**
     *  This is a wrapper around the new stopForeground method, using the older      
     *  APIs if it is not available.      
     */
    void stopForegroundCompat(int id) {
        if (mReflectFlg) {
            // If we have the new stopForeground API, then use it.              
            if (mStopForeground != null) {
                mStopForegroundArgs[0] = Boolean.TRUE;
                invokeMethod(mStopForeground, mStopForegroundArgs);
                return;
            }
            // Fall back on the old API.  Note to cancel BEFORE changing the
            // foreground state, since we could be killed at that point.              
            mNM.cancel(id);
            mSetForegroundArgs[0] = Boolean.FALSE;
            invokeMethod(mSetForeground, mSetForegroundArgs);
        } else {
            /* 还可以使用以下方法，当sdk大于等于5时，调用sdk现有的方法stopForeground停止前台运行，
            * 否则调用反射取得的sdk level 5（对应Android 2.0）以下才有的旧方法setForeground停止前台运行 
            */
            if (Build.VERSION.SDK_INT >= 5) {
                stopForeground(true);
            } else {
                // Fall back on the old API.  Note to cancel BEFORE changing the
                //  foreground state, since we could be killed at that point.
                mNM.cancel(id);
                mSetForegroundArgs[0] = Boolean.FALSE;
                invokeMethod(mSetForeground, mSetForegroundArgs);
            }
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
