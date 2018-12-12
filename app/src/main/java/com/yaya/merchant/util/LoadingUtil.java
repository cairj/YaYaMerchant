package com.yaya.merchant.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.yaya.merchant.widgets.dialog.LoadingProgressDialog;

/**
 * Created by 蔡蓉婕 on 2017/6/26.
 */

public class LoadingUtil {
    private static LoadingProgressDialog loadingProgressDialog;
    private static Handler mUIHandler = new Handler(Looper.getMainLooper());

    public static void showAsyncProgressDialog(final Context context, final String loading) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (loadingProgressDialog == null || loadingProgressDialog.getContext() != context) {
                    loadingProgressDialog = new LoadingProgressDialog(context);
                    loadingProgressDialog.setCancelable(false);
                }

                loadingProgressDialog.setLoadingText(loading);

                if (!loadingProgressDialog.isShowing()) {
                    try {
                        loadingProgressDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public static void showAsyncProgressDialog(Context context) {
        showAsyncProgressDialog(context, "");
    }

    public static void hideProcessingIndicator() {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (loadingProgressDialog != null && loadingProgressDialog.getContext() != null) {
                    loadingProgressDialog.dismiss();
                }
            }
        });
    }
}
