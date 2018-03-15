package com.yaya.merchant.action;

import com.yaya.merchant.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by 蔡蓉婕 on 2018/3/7.
 */

public class MainAction {

    public static void getHomeData(Callback callback){
        OkHttpUtils.get().url(Urls.HOME_DATA)
                .build().execute(callback);
    }

    public static void getUserData(Callback callback){
        OkHttpUtils.get().url(Urls.USER_DATA)
                .build().execute(callback);
    }

}
