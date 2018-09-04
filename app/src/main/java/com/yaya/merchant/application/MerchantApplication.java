package com.yaya.merchant.application;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.android.tpush.XGPushConfig;
import com.yaya.merchant.net.HeaderInterceptor;
import com.yaya.merchant.net.LogInterceptor;
import com.yaya.merchant.util.Constants;
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
        /*腾讯信鸽推送*/
        //开启debug日志数据
        XGPushConfig.enableDebug(this, true);
        XGPushConfig.enableOtherPush(getApplicationContext(), true);
        XGPushConfig.setHuaweiDebug(true);
        XGPushConfig.setMiPushAppId(getApplicationContext(), Constants.XG_PUSH_APP_ID);
        XGPushConfig.setMiPushAppKey(getApplicationContext(), Constants.XG_PUSH_APP_KEY);
        XGPushConfig.setMzPushAppId(this, Constants.XG_PUSH_APP_ID);
        XGPushConfig.setMzPushAppKey(this, Constants.XG_PUSH_APP_KEY);

        //讯飞语音
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=5aae2858");
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
