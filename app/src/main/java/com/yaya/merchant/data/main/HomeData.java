package com.yaya.merchant.data.main;

import com.toroke.okhttp.BaseData;

import java.io.Serializable;

/**
 * Created by 蔡蓉婕 on 2018/3/7.
 */

public class HomeData implements Serializable{

    private String collectionAmount;//今日实收金额
    private String orderNumber;//今日交易笔数
    private String visitorCount;//今日访客数量
    private String addMemberCount;//今日新增会员
    private String memberTotal;//会员总数
    private String orderPriceTotal;//订单总额
    private String orderPriceCount;//总订单数量
    private String orderCount;//订单数量

    public String getCollectionAmount() {
        return collectionAmount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getVisitorCount() {
        return visitorCount;
    }

    public String getAddMemberCount() {
        return addMemberCount;
    }

    public String getMemberTotal() {
        return memberTotal;
    }

    public String getOrderPriceTotal() {
        return orderPriceTotal;
    }

    public String getOrderPriceCount() {
        return orderPriceCount;
    }

    public String getOrderCount() {
        return orderCount;
    }
}
