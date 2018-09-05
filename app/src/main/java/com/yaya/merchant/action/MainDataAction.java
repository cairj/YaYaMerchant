package com.yaya.merchant.action;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseData;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.data.Article;
import com.yaya.merchant.data.account.BillData;
import com.yaya.merchant.data.account.BillDetailData;
import com.yaya.merchant.data.account.BillListData;
import com.yaya.merchant.data.account.Member;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.util.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Response;

/**
 * Created by admin on 2018/3/11.
 */

public class MainDataAction {

    //获取入账信息
    public static JsonResponse<BaseRowData<BillData>> getAccountList(String keyword, String payState, String payType,
                                                                     String startTime, String endTime, String page,
                                                                     String pageSize) throws IOException, JSONException {
        if (TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.BILL_HOUSTON)
                .addParams("page", page)
                .addParams("pageSize", pageSize);
        if (!TextUtils.isEmpty(payState)) {
            builder.addParams("type", payState);
        }
        if (!TextUtils.isEmpty(payType)) {
            builder.addParams("pay_type", payType);
        }
        if (!TextUtils.isEmpty(startTime)) {
            builder.addParams("startTime", startTime);
        }
        if (!TextUtils.isEmpty(endTime)) {
            builder.addParams("endTime", endTime);
        }
        if (!TextUtils.isEmpty(keyword)) {
            builder.addParams("keyword", keyword);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        JSONObject responseObject = new JSONObject(response.body().string());
        JSONObject dataObject = new JSONObject(responseObject.getString("data"));
        BaseRowData<BillData> billDataBaseRowData = new BaseRowData<>();
        ArrayList<BillData> dataList = new ArrayList<>();
        if (dataObject.get("rows") instanceof JSONObject) {
            JSONObject jsonObject = dataObject.getJSONObject("rows");
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                BillData billData = gson.fromJson(jsonObject.getString(key), BillData.class);
                billData.setDate(key);
                dataList.add(billData);
            }
        }
        billDataBaseRowData.setRows(dataList);
        billDataBaseRowData.setTotal(dataObject.getInt("total"));
        JsonResponse<BaseRowData<BillData>> jsonResponse = new JsonResponse<>();
        jsonResponse.setData(billDataBaseRowData);
        jsonResponse.setCode(Constants.RESPONSE_SUCCESS);
        return jsonResponse;
    }

    public static void searchMerchant(Callback callback) {
        GetBuilder builder = OkHttpUtils.get().url(Urls.BILL_GET_ALL_STORE);
        builder.build().execute(callback);
    }


    //获取入账信息
    public static JsonResponse<BaseRowData<BillData>> getMemberBillList(String storeId, String orderType,
                                                                        String startTime, String endTime, String page,
                                                                        String pageSize, String keyword) throws IOException, JSONException {
        if (TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.BILL_GET_MEMBER_BILL)
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
        if (!TextUtils.isEmpty(storeId)) {
            builder.addParams("store_id", storeId);
        }
        if (!TextUtils.isEmpty(keyword)) {
            builder.addParams("keyword", keyword);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        JSONObject responseObject = new JSONObject(response.body().string());
        JSONObject dataObject = new JSONObject(responseObject.getString("data"));
        BaseRowData<BillData> billDataBaseRowData = new BaseRowData<>();
        ArrayList<BillData> dataList = new ArrayList<>();
        if (dataObject.get("rows") instanceof JSONObject) {
            JSONObject jsonObject = dataObject.getJSONObject("rows");
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                BillData billData = gson.fromJson(jsonObject.getString(key), BillData.class);
                billData.setDate(key);
                dataList.add(billData);
            }
        }
        billDataBaseRowData.setRows(dataList);
        billDataBaseRowData.setTotal(dataObject.getInt("total"));
        JsonResponse<BaseRowData<BillData>> jsonResponse = new JsonResponse<>();
        jsonResponse.setData(billDataBaseRowData);
        jsonResponse.setCode(Constants.RESPONSE_SUCCESS);
        return jsonResponse;
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

    //对账
    public static void getBalanceAccount(String storeId, String startTime, String endTime, Callback callback) {
        if (TextUtils.isEmpty(storeId)) {
            return;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.BILL_GET_RECONCILIATION)
                .addParams("storeId", storeId);
        if (!TextUtils.isEmpty(startTime)) {
            builder.addParams("startTime", startTime);
        }
        if (!TextUtils.isEmpty(endTime)) {
            builder.addParams("endTime", endTime);
        }
        builder.build().execute(callback);
    }

    //获取账单列表
    public static JsonResponse<BillListData> getBillList(String startTime, String endTime,
                                                         String page, String pageSize) throws IOException {
        if (TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize) || TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return null;
        }
        GetBuilder builder = OkHttpUtils.get().url(Urls.BILL_GET_BILL_LIST)
                .addParams("page", page)
                .addParams("pageSize", pageSize)
                .addParams("startTime", startTime)
                .addParams("endTime", endTime);
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BillListData>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

    //获取会员数据
    public static void getMemberData(Callback callback) {
        OkHttpUtils.get().url(Urls.GET_MEMBER_DATA).build().execute(callback);
    }

    // 获取文章通知列表
    public static JsonResponse<BaseRowData<Article>> getArticleList(String articleType, String page, String pageSize) throws IOException {
        if (TextUtils.isEmpty(page) || TextUtils.isEmpty(pageSize)) {
            return null;
        }
        String url = "";
        switch (articleType){
            case Article.TYPE_NOTIFY:
                url = Urls.GET_ARTICLE_LIST;
                break;
            case Article.TYPE_INFORMATION:
                url = Urls.GET_CONSULT_LIST;
                break;
        }
        Response response = OkHttpUtils.get().url(url)
                .addParams("page", page)
                .addParams("pageSize", pageSize).build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BaseRowData<Article>>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

    //获取收款详情
    public static void getGatheringDetail(String id, String type, Callback callback) {
        OkHttpUtils.get().url(Urls.GET_HOUSTON_DETAIL)
                .addParams("type_alis_id", id)
                .addParams("type", type)
                .build().execute(callback);
    }

}
