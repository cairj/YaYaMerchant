package com.yaya.merchant.action;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.data.order.OrderDetail;
import com.yaya.merchant.data.recharge.RechargeData;
import com.yaya.merchant.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by 蔡蓉婕 on 2018/9/11.
 */

public class RechargeAction {

    public static JsonResponse<RechargeData> getRechargeRecord(String startTime, String endTime,String sign,String payType,
                                                                            String page, String pageSize) throws IOException {
        if (TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.GET_ORDER_LIST)
                .addParams("page", page)
                .addParams("pageSize", pageSize);
        if (!TextUtils.isEmpty(startTime)) {
            builder.addParams("startTime", startTime);
        }
        if (!TextUtils.isEmpty(endTime)) {
            builder.addParams("endTime", endTime);
        }
        if (!TextUtils.isEmpty(sign)) {
            builder.addParams("sign", sign);
        }
        if (!TextUtils.isEmpty(payType)) {
            builder.addParams("pay_type", payType);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<RechargeData>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

}
