package com.yaya.merchant.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.WindowManager;

import com.yaya.merchant.util.sp.SPUtil;
import com.yaya.merchant.util.sp.SpKeys;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 */

public class DeviceParamsUtil {
    private static int mScreenWidth;
    private static int mScreenHeight;
    private static String udid;

    //获取屏幕宽度
    public static int getScreenWidth(Context context) {
        if (mScreenWidth == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            mScreenWidth = wm.getDefaultDisplay().getWidth();
        }
        return mScreenWidth;
    }

    /* 获取屏幕高度 */
    public static int getScreenHeight(Context context) {
        if (mScreenHeight == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            mScreenHeight = wm.getDefaultDisplay().getHeight();
        }
        return mScreenHeight;
    }

    /* 获取状态栏高度 */
    public static int getStatusBarHeight(Context context) {
        int height = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    @SuppressLint("MissingPermission")
    public static String getUdid(Context context) {
        if (udid != null) {
            return udid;
        }

        // 首先获取保存在prefs文件内的数据
        final String id = SPUtil.getString(SpKeys.UDID);
        if (!TextUtils.isEmpty(id) && !"0".equals(id)) {
            return id;
        }

        // 通过 Secure.getString(s_instance.getContentResolver(), Secure.ANDROID_ID);   也可以获取到一个id
        //但是android2.2或者是某些山寨手机使用这个也是有问题的，它会返回一个固定的值 9774d56d682e549c
        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        try {
            if (!"9774d56d682e549c".equals(androidId)) {
                udid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
            } else {
                final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

                //如果前两个都没有获取到udid，那么就在程序启动的时候创建一个随机的uuid
                udid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")).toString() : UUID.randomUUID().toString();
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        SPUtil.putString(SpKeys.UDID, udid);// Write the value out to the prefs file

        return udid;
    }

}
