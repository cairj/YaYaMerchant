package com.yaya.merchant.action;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.data.goods.Goods;
import com.yaya.merchant.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by admin on 2018/9/3.
 */

public class GoodsAction {

    public static void queryGoodsSaleRank(String limit, Callback callback){
        GetBuilder builder = OkHttpUtils.get().url(Urls.QUERY_GOODS_SALE_RANK);
        if (!TextUtils.isEmpty(limit)){
            builder.addParams("limit",limit);
        }
        builder.build().execute(callback);
    }

    public static JsonResponse<BaseRowData<Goods>> queryGoodsList(String keyword, String state, String startDate, String endTime,
                                                                  String page, String pageSize) throws IOException {
        if (TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.QUERY_GOODS_LIST)
                .addParams("page", page)
                .addParams("pageSize", pageSize);
        if (!TextUtils.isEmpty(state)) {
            builder.addParams("state", state);
        }
        if (!TextUtils.isEmpty(startDate)) {
            builder.addParams("start_date", startDate);
        }
        if (!TextUtils.isEmpty(endTime)) {
            builder.addParams("end_time", endTime);
        }
        if (!TextUtils.isEmpty(keyword)) {
            builder.addParams("keyword", keyword);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BaseRowData<Goods>>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

    public static void changeGoodState(String state,String goodsId,Callback callback){
        String url = "";
        switch (state){
            case Goods.STATUS_PUT_AWAY:
                url = Urls.CHANGE_GOODS_STATUS_SOLD_DOWN;
                break;
            case Goods.STATUS_SOLD_OUT:
                url = Urls.CHANGE_GOODS_STATUS_PUT_AWAY;
                break;
            case Goods.STATUS_APPLYING:
                url = Urls.CHANGE_GOODS_STATUS_APPLY_PASS;
                break;
        }
        OkHttpUtils.get().url(url)
                .addParams("goods_id",goodsId)
                .build().execute(callback);
    }

    public static void getGoodsDetail(String goodsId,Callback callback){
        OkHttpUtils.get().url(Urls.GET_GOODS_DETAIL)
                .addParams("goods_id",goodsId)
                .build().execute(callback);
    }

}
