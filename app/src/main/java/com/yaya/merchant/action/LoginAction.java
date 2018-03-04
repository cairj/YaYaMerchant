package com.yaya.merchant.action;

import android.text.TextUtils;

import com.yaya.merchant.net.Urls;
import com.yaya.merchant.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by admin on 2018/3/4.
 */

public class LoginAction {

    public static void login(String username, String password, Callback callback) {
        if (TextUtils.isEmpty(username)){
            ToastUtil.toast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ToastUtil.toast("密码不能为空");
            return;
        }
        OkHttpUtils.post().url(Urls.LOGIN)
                .addParams("usernameOrEmailAddress",username)
                .addParams("password",password)
                .build().execute(callback);
    }

    public static void getPhoneByUser(String username, Callback callback) {
        if (TextUtils.isEmpty(username)){
            ToastUtil.toast("用户名不能为空");
            return;
        }
        OkHttpUtils.post().url(Urls.GET_PHONE_BY_USER)
                .addParams("loginName",username)
                .build().execute(callback);
    }

    public static void sendMessage(String phone, Callback callback) {
        OkHttpUtils.post().url(Urls.SEND_MESSAGE)
                .addParams("phone",phone)
                .build().execute(callback);
    }

    public static void changePassword(String newPassword,String confirmPassword,String userId,Callback callback){
        if (TextUtils.isEmpty(newPassword)){
            ToastUtil.toast("新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)){
            ToastUtil.toast("确认密码不能为空");
            return;
        }
        if (!confirmPassword.equals(newPassword)){
            ToastUtil.toast("确认密码与新密码不一致");
            return;
        }
        OkHttpUtils.post().url(Urls.SEND_MESSAGE)
                .addParams("newPassword",newPassword)
                .addParams("userId",userId)
                .build().execute(callback);
    }

}
