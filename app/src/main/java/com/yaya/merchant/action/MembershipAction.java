package com.yaya.merchant.action;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.data.account.Member;
import com.yaya.merchant.data.membership.MemberShipData;
import com.yaya.merchant.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by 蔡蓉婕 on 2018/10/11.
 */

public class MembershipAction {

    public static JsonResponse<BaseRowData<MemberShipData>> getMerchantInfo(String keyword , String level_id) throws IOException {
        GetBuilder builder = OkHttpUtils.get().url(Urls.GET_MEMBERSHIP_LIST);
        if (!TextUtils.isEmpty(keyword)){
            builder.addParams("keyword",keyword);
        }
        if (!TextUtils.isEmpty(level_id)){
            builder.addParams("level_id",level_id);
        }
        Response response = builder.build().execute();
        Gson gson = new Gson();
        Type type = new TypeToken<JsonResponse<BaseRowData<MemberShipData>>>() {
        }.getType();
        return gson.fromJson(response.body().string(), type);
    }

    public static void getMembershipLevel(Callback callback){
        OkHttpUtils.get().url(Urls.GET_MEMBERSHIP_LEVEL)
                .build().execute(callback);
    }

}
