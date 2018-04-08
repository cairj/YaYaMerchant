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

    public static void login(String username, String password, Callback callback) {
        if (TextUtils.isEmpty(username)) {
            ToastUtil.toast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.toast("密码不能为空");
            return;
        }
        OkHttpUtils.post().url(Urls.LOGIN)
                .addParams("usernameOrEmailAddress", username)
                .addParams("password", password)
                .build().execute(callback);
    }

    public static void getPhoneByUser(String username, Callback callback) {
        if (TextUtils.isEmpty(username)) {
            ToastUtil.toast("用户名不能为空");
            return;
        }
        OkHttpUtils.get().url(Urls.GET_PHONE_BY_USER)
                .addParams("loginName", username)
                .build().execute(callback);
    }

    public static void sendMessage(String phone, Callback callback) {
        OkHttpUtils.get().url(Urls.SEND_MESSAGE)
                .addParams("phone", phone)
                .build().execute(callback);
    }

    public static void changePassword(String newPassword, String confirmPassword, String userId, Callback callback) {
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
        OkHttpUtils.get().url(Urls.SEND_MESSAGE)
                .addParams("newPassword", newPassword)
                .addParams("userId", userId)
                .build().execute(callback);
    }

    public static String registerMerchant(String name, String phone, String storeName, String storeAddress,
                                        String remark) {
        if (TextUtils.isEmpty(name)) {
            return "姓名不能为空";
        }
        if (TextUtils.isEmpty(phone)) {
            return "电话不能为空";
        }
        if (TextUtils.isEmpty(storeName)) {
            return "店名不能为空";
        }
        if (TextUtils.isEmpty(storeAddress)) {
            return "地址不能为空";
        }
        if (TextUtils.isEmpty(remark)) {
            return "街道信息不能为空";
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name)
                    .put("phone", phone)
                    .put("storeName", storeName)
                    .put("storeAddress", storeAddress)
                    .put("remark", remark);
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
        }
    }

}
