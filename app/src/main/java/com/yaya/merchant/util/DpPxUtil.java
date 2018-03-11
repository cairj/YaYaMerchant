package com.yaya.merchant.util;

import android.content.Context;

/**
 * Created by admin on 2018/3/11.
 */

public class DpPxUtil {

    /**
     * 将dp转换为与之相等的px
     */
    public static int dp2px(float dpValue) {
        Context context = AppManager.getAppManager().getCurrentActivity();
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将px转换为与之相等的dp
     */
    public static int px2dp(float pxValue) {
        Context context = AppManager.getAppManager().getCurrentActivity();
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px转换为sp
     */
    public static int px2sp(float pxValue) {
        Context context = AppManager.getAppManager().getCurrentActivity();
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 将sp转换为px
     */
    public static int sp2px(float spValue) {
        Context context = AppManager.getAppManager().getCurrentActivity();
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


}
