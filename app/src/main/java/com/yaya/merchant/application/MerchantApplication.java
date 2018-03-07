package com.yaya.merchant.application;

import android.app.Application;

import com.yaya.merchant.net.HeaderInterceptor;
import com.yaya.merchant.net.LogInterceptor;
import com.yaya.merchant.util.sp.SPUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by 蔡蓉婕 on 2018/3/5.
 */

public class MerchantApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SPUtil.init(this);
        initOkHttp();
    }

    private void initOkHttp() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HeaderInterceptor())//添加请求头
                .addNetworkInterceptor(new LogInterceptor())//添加响应拦截器
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cache(new Cache(getCacheDir(), 2 * 1024 * 1024))
                .build();
        OkHttpUtils.initClient(client);
    }


}
