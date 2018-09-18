package com.yaya.merchant.action;

import com.yaya.merchant.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by 蔡蓉婕 on 2018/9/18.
 */

public class ReportAction {

    public static void getOrderReportData(String startTime, Callback callback){
        OkHttpUtils.get().url(Urls.ORDER_REPORT_DATA)
                .addParams("start_time",startTime)
                .build().execute(callback);
    }

    public static void getSaleReportData(String startTime, Callback callback){
        OkHttpUtils.get().url(Urls.SALE_REPORT_DATA)
                .addParams("start_time",startTime)
                .build().execute(callback);
    }

}
