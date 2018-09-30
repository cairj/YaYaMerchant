package com.yaya.merchant.util;

import android.content.Context;
import android.content.Intent;

import com.yaya.merchant.activity.login.LoginActivity;
import com.yaya.merchant.service.GetOrderService;
import com.yaya.merchant.util.sp.SPUtil;
import com.yaya.merchant.util.sp.SpKeys;

/**
 * Created by 蔡蓉婕 on 2018/8/30.
 */

public class UserHelper {

    public static void logout(Context context){
        SPUtil.putBoolean(SpKeys.IS_LOGIN, false);
        SPUtil.putString(SpKeys.TOKEN, "");
        SPUtil.putInt(SpKeys.USER_TYPE, -1);
        AppManager.getAppManager().finishAllActivity();
        context.startActivity(new Intent(context,LoginActivity.class));

        context.stopService(new Intent(context, GetOrderService.class));
    }

    public static boolean isMerchant(){
        return SPUtil.getInt(SpKeys.USER_TYPE) == Constants.MEMBER_TYPE_MERCHANT;
    }

    public static boolean isAgent(){
        return SPUtil.getInt(SpKeys.USER_TYPE) == Constants.MEMBER_TYPE_AGENT;
    }

}
