package com.yaya.merchant.net;

import com.yaya.merchant.util.StringsUtil;

import java.util.HashMap;
import java.util.Map;

public class UrlH5s {

    private static final String HOST = "http://static.qingkr.cn/html/";

    private static final String CARD_DETAIL = HOST + "cardDetail1.html";//卡片详情页
    public static String getCardDetail(int cardId){
        HashMap<String,String> params=new HashMap();
        params.put("cardId",String.valueOf(cardId));
        return buildQueryString(CARD_DETAIL,params);
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
