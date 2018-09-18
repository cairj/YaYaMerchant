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
            builder.addParams("store_state", status);
        }
        if (!TextUtils.isEmpty(search)) {
            builder.addParams("keyword", search);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BaseRowData<MerchantData>>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

    //获取我的商户列表
    public static JsonResponse<BaseRowData<MerchantData>> getMerchantListByAgent(String categoryId, String search,
                                                                          String page, String pageSize) throws IOException {
        if (TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.GET_MERCHANT_LIST_BY_AGENT)
                .addParams("page", page)
                .addParams("pageSize", pageSize);
        if (!TextUtils.isEmpty(categoryId)) {
            builder.addParams("category_id", categoryId);
        }
        if (!TextUtils.isEmpty(search)) {
            builder.addParams("keyword", search);
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
    public static void getMerchantQrCode(Callback callback) {
        OkHttpUtils.get().url(Urls.GET_MERCHANT_QR_CODE)
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

    //语音提示总开关
    public static void setVoice(String IsVoice, Callback callback) {
        GetBuilder builder = OkHttpUtils.get().url(Urls.SET_VOICE)
                .addParams("voices", IsVoice);
        builder.build().execute(callback);
    }

    //语言开关
    public static void setVoiceSound(String IsVoice, Callback callback) {
        GetBuilder builder = OkHttpUtils.get().url(Urls.SET_VOICE_SOUND)
                .addParams("sounds", IsVoice);
        builder.build().execute(callback);
    }

    //推送开关
    public static void setVoicePush(String IsVoice, Callback callback) {
        GetBuilder builder = OkHttpUtils.get().url(Urls.SET_VOICE_PUSH)
                .addParams("letter", IsVoice);
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
        } else {
            pushFeedBack(content, "", callback);
        }
    }

    private static void pushFeedBack(String content, String img, Callback callback) {
        GetBuilder builder = OkHttpUtils.get().url(Urls.PUSH_FEED_BACK)
                .addParams("content", content);
        if (TextUtils.isEmpty(img)) {
            builder.addParams("img", img);
        }
        builder.build().execute(callback);
    }

    //核销
    public static void verification(String verificationSn, Callback callback) {
        if (TextUtils.isEmpty(verificationSn)) {
            ToastUtil.toast("订单号不能为空");
            return;
        }
        OkHttpUtils.get().url(Urls.VERIFICATION_SET)
                .addParams("order_id", verificationSn)
                .build().execute(callback);
    }

    //根据二维码获取订单信息
    public static void getOrderByQrCode(String verificationSn, Callback callback){
        if (TextUtils.isEmpty(verificationSn)) {
            ToastUtil.toast("核销码不能为空");
            return;
        }
        OkHttpUtils.get().url(Urls.VERIFICATION_INDEX)
                .addParams("qr_code", verificationSn)
                .build().execute(callback);
    }

    //客服电话
    public static void getServicePhone(Callback callback) {
        OkHttpUtils.get().url(Urls.GET_SERVICE_PHONE)
                .build().execute(callback);
    }

    //更改用户头像
    public static void changeProfilePic(LocalMedia media, final Callback callback) {
        if (media == null) {
            return;
        }
        File file = new File(media.getPath());
        new ImgLoadPayUntil(file, new ImgLoadPayUntil.getStringListener() {
            @Override
            public void getString(String str) {
                OkHttpUtils.get().url(Urls.CHANGE_PROFILE_PIC)
                        .addParams("head_img_url", str)
                        .build().execute(callback);
            }
        }).execute("");
    }

    //更改密码
    public static void changePassword(String oldPassword,String newPassword,String confirmPassword, Callback callback) {
        if (TextUtils.isEmpty(oldPassword)){
            ToastUtil.toast("当前密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(newPassword)){
            ToastUtil.toast("新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)){
            ToastUtil.toast("第二次输入的密码不能为空");
            return;
        }
        if (!newPassword.equals(confirmPassword)){
            ToastUtil.toast("两次新密码不相同，请重新确认并输入");
            return;
        }
        OkHttpUtils.get().url(Urls.CHANGE_PASSWORD)
                .addParams("old_password", oldPassword)
                .addParams("one_password", newPassword)
                .addParams("two_password", newPassword)
                .build().execute(callback);
    }

    //签约信息
    public static void getContractInformation(Callback callback){
        OkHttpUtils.post().url(Urls.GET_CONTRACT_INFORMATION)
                .build().execute(callback);
    }

    //商户资料
    public static void getMerchantInfo(Callback callback){
        OkHttpUtils.post().url(Urls.GET_MERCHANT_INFO)
                .build().execute(callback);
    }

    //商户详情
    public static void getMerchantDetail(String shopId,Callback callback){
        OkHttpUtils.post().url(Urls.GET_MERCHANT_DETAIL)
                .addParams("shop_id",shopId)
                .build().execute(callback);
    }

    //商户分类
    public static void getMerchantClassify(Callback callback){
        OkHttpUtils.post().url(Urls.GET_MERCHANT_CLASSIFY)
                .build().execute(callback);
    }

    //商户报表
    public static void getMerchantReportForms(String url,String shopId,Callback callback){
        OkHttpUtils.post().url(url)
                .addParams("shop_id",shopId)
                .build().execute(callback);
    }

}
