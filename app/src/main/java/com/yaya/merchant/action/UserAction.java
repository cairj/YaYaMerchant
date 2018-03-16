package com.yaya.merchant.action;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.data.user.MerchantData;
import com.yaya.merchant.data.withdraw.WithdrawMoneyRecord;
import com.yaya.merchant.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;

import java.io.IOException;
import java.lang.reflect.Type;

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

}
