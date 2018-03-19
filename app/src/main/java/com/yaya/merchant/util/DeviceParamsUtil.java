package com.yaya.merchant.util;

import android.content.Context;
import android.view.WindowManager;

/**
 */

public class DeviceParamsUtil {
    private static int mScreenWidth;
    private static int mScreenHeight;

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

}
