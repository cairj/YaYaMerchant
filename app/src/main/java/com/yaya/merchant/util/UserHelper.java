package com.yaya.merchant.util;

import android.content.Context;
import android.content.Intent;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.action.LoginAction;
import com.yaya.merchant.activity.login.LoginActivity;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.sp.SPUtil;
import com.yaya.merchant.util.sp.SpKeys;

/**
 * Created by 蔡蓉婕 on 2018/8/30.
 */

public class UserHelper {

    public static void logout(final Context context){
        LoadingUtil.showAsyncProgressDialog(context);
        LoginAction.logout(new GsonCallback<String>(String.class) {
            @Override
            public void onSucceed(JsonResponse<String> response) {
                SPUtil.putBoolean(SpKeys.IS_LOGIN, false);
                SPUtil.putString(SpKeys.TOKEN, "");
                SPUtil.putInt(SpKeys.USER_TYPE, -1);
                AppManager.getAppManager().finishAllActivity();
                context.startActivity(new Intent(context,LoginActivity.class));
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                LoadingUtil.hideProcessingIndicator();
            }
        });
    }

    public static boolean isMerchant(){
        return SPUtil.getInt(SpKeys.USER_TYPE) == Constants.MEMBER_TYPE_MERCHANT;
    }

    public static boolean isAgent(){
        return SPUtil.getInt(SpKeys.USER_TYPE) == Constants.MEMBER_TYPE_AGENT;
    }

}
