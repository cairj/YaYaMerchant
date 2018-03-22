package com.yaya.merchant.data.account;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/14.
 */

public class Member implements Serializable {

    //绑定方式
    public static final String BIND_TYPE_ALL="";//全部状态
    public static final String BIND_WE_CHAT="1";//绑定微信
    public static final String BIND_ALI_PAY="2";//绑定支付宝
    public static final String BIND_WE_CHAT_AND_ALI_PAY="3";//绑定支付宝和微信
    public static final String[] BIND_TYPE={"全部状态","绑定微信","绑定支付宝","绑定支付宝和微信"};
    public static final String[] BIND_TYPE_ID={BIND_TYPE_ALL,BIND_WE_CHAT,BIND_ALI_PAY,BIND_WE_CHAT_AND_ALI_PAY};

    private String mobile;//13959830580",
    private String headImgUrl;
    private String name;
    private String bindName;

    public String getMobile() {
        return mobile;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public String getName() {
        return name;
    }

    public String getBindName() {
        return bindName;
    }
}
