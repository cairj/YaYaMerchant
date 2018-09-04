package com.yaya.merchant.action;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by admin on 2018/3/4.
 */

public class LoginAction {

    public static void login(String username, String password,String xinGeToken,int memberType, Callback callback) {
        if (TextUtils.isEmpty(username)) {
            ToastUtil.toast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.toast("密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(xinGeToken)) {
            ToastUtil.toast("信鸽token不能为空");
            return;
        }
        OkHttpUtils.get().url(Urls.LOGIN)
                .addParams("user_name", username)
                .addParams("password", password)
                .addParams("xinge_token", xinGeToken)
                .addParams("user_type", String.valueOf(memberType))
                .build().execute(callback);
    }

    public static void sendMessage(String username,int memberType, Callback callback) {
        if (TextUtils.isEmpty(username)) {
            ToastUtil.toast("用户名不能为空");
            return;
        }
        OkHttpUtils.get().url(Urls.SEND_MESSAGE)
                .addParams("user_name", username)
                .addParams("user_type", String.valueOf(memberType))
                .build().execute(callback);
    }

    public static void verification(String userName, String code, Callback callback) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.toast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.toast("验证码不能为空");
            return;
        }
        OkHttpUtils.get().url(Urls.VERIFICATION_CODE)
                .addParams("user_name", userName)
                .addParams("code", code)
                .build().execute(callback);
    }

    public static void changePassword(String newPassword, String confirmPassword, String token, Callback callback) {
        if (TextUtils.isEmpty(newPassword)) {
            ToastUtil.toast("新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            ToastUtil.toast("确认密码不能为空");
            return;
        }
        if (!confirmPassword.equals(newPassword)) {
            ToastUtil.toast("确认密码与新密码不一致");
            return;
        }
        OkHttpUtils.get().url(Urls.RESET_PASSWORD)
                .addParams("one_password", newPassword)
                .addParams("two_password", confirmPassword)
                .addParams("token", token)
                .build().execute(callback);
    }

    public static void registerMerchant(String name, String phone, String storeName, String storeAddress,
                                          int provinceId, int cityId, int districtId,Callback callback) {
        if (TextUtils.isEmpty(name)) {
            ToastUtil.toast("姓名不能为空");
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast("电话不能为空");
        }
        if (TextUtils.isEmpty(storeName)) {
            ToastUtil.toast("店名不能为空");
        }
        if (TextUtils.isEmpty(storeAddress)) {
            ToastUtil.toast( "地址不能为空");
        }

        OkHttpUtils.get().url(Urls.REGISTER_MERCHAT).addParams("name", name)
                .addParams("tel", phone)
                .addParams("shop_name", storeName)
                .addParams("address", storeAddress)
                .addParams("province_id", String.valueOf(provinceId))
                .addParams("city_id", String.valueOf(cityId))
                .addParams("area_id", String.valueOf(districtId))
                .build().execute(callback);

        /*JSONObject jsonObject = new JSONObject();
        try {
            jsonObject
            MediaType json = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Urls.REGISTER_MERCHAT)
                    .post(RequestBody.create(json, jsonObject.toString()))
                    .build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Type type = new TypeToken<JsonResponse<String>>() {
                }.getType();
                JsonResponse<String> data = new Gson().fromJson(response.body().string(), type);
                return data.getData().getData();
            }else {
                return "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }*/
    }

}
