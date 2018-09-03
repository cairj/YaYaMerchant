package com.yaya.merchant.action;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.data.order.ExpressCompany;
import com.yaya.merchant.data.order.OrderDetail;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by admin on 2018/3/25.
 */

public class OrderAction {

    //获取订单列表
    public static JsonResponse<BaseRowData<OrderDetail>> getOrderList(String startTime, String endTime,String isScan,String orderStatus,
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
        if (!TextUtils.isEmpty(isScan)) {
            builder.addParams("is_scan", isScan);
        }
        if (!TextUtils.isEmpty(orderStatus)) {
            builder.addParams("order_status", orderStatus);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BaseRowData<OrderDetail>>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

    //订单详情
    public static void getOrderDetail(String orderSn, Callback callback) {
        OkHttpUtils.get().url(Urls.GET_ORDER_DETAIL)
                .addParams("orderSn", orderSn)
                .build().execute(callback);
    }

    //获取快递公司列表
    public static void getExpressCompanyList(Callback callback) {
        OkHttpUtils.get().url(Urls.GET_EXPRESS_COMPANY_LIST)
                .build().execute(callback);
    }

    //发货
    public static void deliverGoods(ExpressCompany expressCompany, OrderDetail orderDetail, String expressId, Callback callback) {
        if (TextUtils.isEmpty(orderDetail.getId()) || TextUtils.isEmpty(orderDetail.getMemberId())
                || TextUtils.isEmpty(orderDetail.getOrderSn())) {
            ToastUtil.toast("此订单不能发货");
            return;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.DELIVER_GOODS)
                .addParams("id", orderDetail.getId())
                .addParams("memberId", orderDetail.getMemberId())
                .addParams("orderSn", orderDetail.getOrderSn());
        if (expressCompany != null) {
            if (!TextUtils.isEmpty(expressCompany.getExpressName())) {
                builder.addParams("expressName", expressCompany.getExpressName());
            }
            if (TextUtils.isEmpty(expressCompany.getExpressNo())) {
                builder.addParams("expressNo", expressCompany.getExpressNo());
            }
        }
        if (!TextUtils.isEmpty(expressId)) {
            builder.addParams("expressId", expressId);
        }
        builder.build().execute(callback);
    }

    public static void allowRefund(String orderSn, Callback callback) {
        OkHttpUtils.get().url(Urls.ALLOW_REFUND)
                .addParams("orderSn", orderSn)
                .build().execute(callback);
    }

    public static void disallowRefund(String orderSn, String refundFailReason, String memberId, Callback callback) {
        GetBuilder builder = OkHttpUtils.get().url(Urls.DISALLOW_REFUND)
                .addParams("orderSn", orderSn)
                .addParams("memberId", memberId);
        if (!TextUtils.isEmpty(refundFailReason)) {
            builder.addParams("RefundFailReason", refundFailReason);
        }
        builder.build().execute(callback);
    }

}
