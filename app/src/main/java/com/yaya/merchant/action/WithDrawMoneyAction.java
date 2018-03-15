package com.yaya.merchant.action;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.data.account.Member;
import com.yaya.merchant.data.withdraw.WithdrawMoneyRecord;
import com.yaya.merchant.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * 提现
 */

public class WithDrawMoneyAction {

    public static void getBankCard(Callback callback){
        OkHttpUtils.get().url(Urls.GET_BANK_CARD)
                .build().execute(callback);
    }

    public static void getMemberBalance(Callback callback){
        OkHttpUtils.get().url(Urls.GET_MEMBER_BALANCE)
                .build().execute(callback);
    }

    public static void getWithDrawMoney(String amount,Callback callback){
        if (TextUtils.isEmpty(amount)){
            return;
        }
        OkHttpUtils.get().url(Urls.WITH_DRAW_MONEY)
                .addParams("amount",amount)
                .build().execute(callback);
    }

    //获取提现记录列表
    public static JsonResponse<BaseRowData<WithdrawMoneyRecord>> getWithdrawMoneyRecord(String cashoutType, String status,
                                                                               String startTime, String endTime,
                                                                               String page, String pageSize) throws IOException {
        if (TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.GET_WITH_DRAW_MONEY_RECORD)
                .addParams("page", page)
                .addParams("pageSize", pageSize);
        if (!TextUtils.isEmpty(cashoutType)) {
            builder.addParams("search", cashoutType);
        }
        if (!TextUtils.isEmpty(status)) {
            builder.addParams("memberstate", status);
        }
        if (!TextUtils.isEmpty(startTime)) {
            builder.addParams("startTime", startTime);
        }
        if (!TextUtils.isEmpty(endTime)) {
            builder.addParams("endTime", endTime);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BaseRowData<WithdrawMoneyRecord>>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

}
