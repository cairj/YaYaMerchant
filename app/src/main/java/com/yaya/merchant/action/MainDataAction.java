package com.yaya.merchant.action;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.data.account.BillData;
import com.yaya.merchant.data.account.Member;
import com.yaya.merchant.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by admin on 2018/3/11.
 */

public class MainDataAction {

    //获取入账信息
    public static JsonResponse<BaseRowData<BillData>> getAccountList(String storeId, String payState, String payType,
                                                                     String startTime, String endTime, String page,
                                                                     String pageSize) throws IOException {
        if (TextUtils.isEmpty(storeId) || TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.BILL_HOUSTON)
                .addParams("storeId", storeId)
                .addParams("page", page)
                .addParams("pageSize", pageSize);
        if (!TextUtils.isEmpty(payState)) {
            builder.addParams("payState", payState);
        }
        if (!TextUtils.isEmpty(payType)) {
            builder.addParams("payType", payType);
        }
        if (!TextUtils.isEmpty(startTime)) {
            builder.addParams("startTime", startTime);
        }
        if (!TextUtils.isEmpty(endTime)) {
            builder.addParams("endTime", endTime);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BaseRowData<BillData>>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

    public static void searchMerchant(String search, Callback callback) {
        GetBuilder builder = OkHttpUtils.get().url(Urls.BILL_GET_ALL_STORE);
        if (!TextUtils.isEmpty(search)) {
            builder.addParams("Search", search);
        }
        builder.build().execute(callback);
    }


    //获取入账信息
    public static JsonResponse<BaseRowData<BillData>> getMemberBillList(String storeId, String orderType,
                                                                        String startTime, String endTime, String page,
                                                                        String pageSize) throws IOException {
        if (TextUtils.isEmpty(storeId) || TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.BILL_GET_MEMBER_BILL)
                .addParams("storeId", storeId)
                .addParams("page", page)
                .addParams("pageSize", pageSize);
        if (!TextUtils.isEmpty(orderType)) {
            builder.addParams("orderType", orderType);
        }
        if (!TextUtils.isEmpty(startTime)) {
            builder.addParams("startTime", startTime);
        }
        if (!TextUtils.isEmpty(endTime)) {
            builder.addParams("endTime", endTime);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BaseRowData<BillData>>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

    //获取会员列表
    public static JsonResponse<BaseRowData<Member>> getMemberList(String search, String memberstate,
                                                                  String page, String pageSize) throws IOException {
        if (TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.GET_MEMBER_MANAGER_LIST)
                .addParams("page", page)
                .addParams("pageSize", pageSize);
        if (!TextUtils.isEmpty(search)) {
            builder.addParams("search", search);
        }
        if (!TextUtils.isEmpty(memberstate)) {
            builder.addParams("memberstate", memberstate);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BaseRowData<Member>>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

}
