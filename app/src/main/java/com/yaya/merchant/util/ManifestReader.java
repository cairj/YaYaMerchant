package com.yaya.merchant.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class ManifestReader {

    /** 返回友盟的渠道名 */
    public static String getChannel(Application application){
        ApplicationInfo appInfo = null;
        try {
            appInfo = application.getPackageManager()
                    .getApplicationInfo(application.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String channel = appInfo.metaData.getString("UMENG_CHANNEL");
        return channel;
    }

    /**
     * 获取Application标签下的meta-data值
     * @param context
     * @param key
     * @return
     */
    public static String getMetaDataInApplication(Context context, String key){
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (applicationInfo == null){
            return "";
        }
        String value = applicationInfo.metaData.getString(key);
        return value;
    }
}
