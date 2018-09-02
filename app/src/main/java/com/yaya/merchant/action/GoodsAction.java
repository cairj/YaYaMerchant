package com.yaya.merchant.action;

import android.text.TextUtils;

import com.yaya.merchant.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.Callback;

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

}
