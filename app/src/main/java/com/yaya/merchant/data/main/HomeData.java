package com.yaya.merchant.data.main;

import com.google.gson.annotations.SerializedName;
import com.toroke.okhttp.BaseData;

import java.io.Serializable;

/**
 * Created by 蔡蓉婕 on 2018/3/7.
 */

public class HomeData implements Serializable{

    private String collectionAmount;//今日实收金额
    @SerializedName("today_order_total")
    private String orderNumber;//今日交易笔数
    @SerializedName("today_order_price")
    private String orderPrice;//今日访客数量
    @SerializedName("add_member_count")
    private String addMemberCount;//今日新增会员
    @SerializedName("member_total")
    private String memberTotal;//会员总数
    @SerializedName("order_price")
    private String orderPriceTotal;//订单总额
    @SerializedName("order_total")
    private String orderPriceCount;//总订单数量
    private String orderCount;//订单数量

    @SerializedName("yesterday_order_price")
    private String yesterdayOrderPrice;// 0.17, // 昨天交易金总额
    @SerializedName("yesterday_order_total")
    private String yesterdayOrderTotal;// 12, // 昨天订单总笔数
    @SerializedName("month_order_price")
    private String monthOrderPrice;// 1435.92, // 昨天交易金总额
    @SerializedName("month_order_total")
    private String monthOrderTotal;// 107, // 昨天订单总笔数

    public String getCollectionAmount() {
        return collectionAmount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderPrice() {
        return orderPrice;
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

    public String getYesterdayOrderPrice() {
        return yesterdayOrderPrice;
    }

    public String getYesterdayOrderTotal() {
        return yesterdayOrderTotal;
    }

    public String getMonthOrderPrice() {
        return monthOrderPrice;
    }

    public String getMonthOrderTotal() {
        return monthOrderTotal;
    }
}
