package com.yaya.merchant.net;

import com.yaya.merchant.util.StringsUtil;

import java.util.HashMap;
import java.util.Map;

public class UrlH5s {

    private static final String HOST = "http://mobile.xinghezhijia.com/#/";

    private static final String ARTICLES_DETAIL = HOST + "shop";//通知、咨询详情页
    public static String getArticleDetail(String id){
        HashMap<String,String> params=new HashMap();
        params.put("id",String.valueOf(id));
        return buildQueryString(ARTICLES_DETAIL,params);
    }

    public static final String buildQueryString(String baseUrl, Map<String, String> params) {
        String url = "";
        try {
            url = StringsUtil.buildQueryString(baseUrl, params, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

}
