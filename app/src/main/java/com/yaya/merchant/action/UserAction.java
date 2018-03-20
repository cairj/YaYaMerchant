package com.yaya.merchant.action;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.entity.LocalMedia;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.data.user.EmployeeData;
import com.yaya.merchant.data.user.MerchantData;
import com.yaya.merchant.data.withdraw.WithdrawMoneyRecord;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.util.imageloader.ImgLoadPayUntil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;

/**
 * Created by admin on 2018/3/16.
 */

public class UserAction {

    //获取我的门店列表
    public static JsonResponse<BaseRowData<MerchantData>> getMerchantList(String status, String search,
                                                                          String page, String pageSize) throws IOException {
        if (TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.GET_MERCHANT_LIST)
                .addParams("page", page)
                .addParams("pageSize", pageSize);
        if (!TextUtils.isEmpty(status)) {
            builder.addParams("status", status);
        }
        if (!TextUtils.isEmpty(search)) {
            builder.addParams("search", search);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BaseRowData<MerchantData>>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

    //获取我的员工列表
    public static JsonResponse<BaseRowData<EmployeeData>> getEmployeeList(String search,
                                                                          String page, String pageSize) throws IOException {
        if (TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.GET_EMPLOYEE_LIST)
                .addParams("page", page)
                .addParams("pageSize", pageSize);
        if (!TextUtils.isEmpty(search)) {
            builder.addParams("search", search);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BaseRowData<EmployeeData>>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

    //获取门店二维码
    public static void getMerchantQrCode(String storeId, Callback callback) {
        OkHttpUtils.get().url(Urls.GET_MERCHANT_QR_CODE)
                .addParams("storeId", storeId)
                .build().execute(callback);
    }

    //绑定银行卡
    public static void getBindInfo(Callback callback) {
        OkHttpUtils.get().url(Urls.GET_BIND_INFO)
                .build().execute(callback);
    }

    //基本信息
    public static void getInformation(Callback callback) {
        OkHttpUtils.get().url(Urls.GET_INFORMATION)
                .build().execute(callback);
    }

    //语音提醒设置情况
    public static void getVoiceSet(Callback callback) {
        OkHttpUtils.get().url(Urls.SET_VOICE_INDEX)
                .build().execute(callback);
    }

    public static void setVoice(String IsVoice, String VoiceType, Callback callback) {
        GetBuilder builder = OkHttpUtils.get().url(Urls.SET_VOICE)
                .addParams("IsVoice", IsVoice);
        if (!TextUtils.isEmpty(VoiceType)) {
            builder.addParams("VoiceType", VoiceType);
        }
        builder.build().execute(callback);
    }

    //语音门店设置
    public static JsonResponse<BaseRowData<Merchant>> getMerchantVoiceSettingList(String search,
                                                                                  String page, String pageSize) throws IOException {
        if (TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.GET_MERCHANT_VOICE_INDEX)
                .addParams("page", page)
                .addParams("pageSize", pageSize);
        if (!TextUtils.isEmpty(search)) {
            builder.addParams("search", search);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BaseRowData<Merchant>>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

    //设置门店语音
    public static void setMerchantVoice(JSONArray jsonArray, Callback callback) {
        OkHttpUtils.get().url(Urls.SET_MERCHANT_VOICE)
                .addParams("model", jsonArray.toString())
                .build().execute(callback);
    }

    //意见反馈
    public static void postFeedBack(final String content, final List<LocalMedia> imgList, final Callback callback) {
        if (TextUtils.isEmpty(content)) {
            ToastUtil.toast("意见内容不能为空");
            return;
        }
        final StringBuffer img = new StringBuffer();
        if (imgList != null && !imgList.isEmpty()) {
            for (int i = 0; i < imgList.size(); i++) {
                File file = new File(imgList.get(i).getPath());
                final int finalI = i;
                new ImgLoadPayUntil(file, new ImgLoadPayUntil.getStringListener() {
                    @Override
                    public void getString(String str) {
                        if (finalI != 0) {
                            img.append(",");
                        }
                        img.append(str);
                        if (finalI == imgList.size() - 1) {//最后一张
                            pushFeedBack(content, img.toString(), callback);
                        }
                    }
                }).execute("");
            }
        }
    }

    public static void pushFeedBack(String content, String img, Callback callback) {
        OkHttpUtils.get().url(Urls.PUSH_FEED_BACK)
                .addParams("content", content)
                .addParams("img", img)
                .build().execute(callback);
    }

}
