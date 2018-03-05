package com.yaya.merchant.application;

import android.app.Application;

import com.yaya.merchant.util.sp.SPUtil;

/**
 * Created by 蔡蓉婕 on 2018/3/5.
 */

public class MerchantApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SPUtil.init(this);
    }
}
