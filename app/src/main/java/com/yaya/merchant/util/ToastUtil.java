package com.yaya.merchant.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 2018/3/4.
 */

public class ToastUtil {

    public static void toast(String msg) {
        toast(AppManager.getAppManager().getCurrentActivity(),msg);
    }

    public static void toast(Context context, String msg) {
        toast(context,msg,Toast.LENGTH_SHORT);
    }

    public static void toast(Context context, String msg, int duration) {
        Toast.makeText(context, msg, duration);
    }

}
