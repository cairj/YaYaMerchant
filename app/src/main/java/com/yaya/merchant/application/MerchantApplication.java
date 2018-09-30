package com.yaya.merchant.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.yaya.merchant.net.HeaderInterceptor;
import com.yaya.merchant.net.LogInterceptor;
import com.yaya.merchant.util.sp.SPUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by 蔡蓉婕 on 2018/3/5.
 */

public class MerchantApplication extends Application {

    private static MerchantApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        SPUtil.init(this);
        initOkHttp();
        String processName = getProcessName(this, android.os.Process.myPid());
        if(processName != null){
            boolean defaultProcess = processName.equals("com.yaya.merchant");
            if (defaultProcess) {
                //讯飞语音
                SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=5aae2858");
            }
        }
    }

    private void initOkHttp() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HeaderInterceptor())//添加请求头
                .addNetworkInterceptor(new LogInterceptor())//添加响应拦截器
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cache(new Cache(getCacheDir(), 2 * 1024 * 1024))
                .build();
        OkHttpUtils.initClient(client);
    }

    public static MerchantApplication getApplication(){
        return mInstance;
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

}
