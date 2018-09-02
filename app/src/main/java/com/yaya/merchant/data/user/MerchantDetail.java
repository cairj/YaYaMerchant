package com.yaya.merchant.data.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/9/2.
 */

public class MerchantDetail implements Serializable {

    @SerializedName("user_count")
    private String userCount;//商家用户总数"
    @SerializedName("goods_count")
    private String goodsCount;//商家商品总数(包含上下架的商品)",
    @SerializedName("order_num")
    private String orderNum;//订单总数量",
    @SerializedName("money_count")
    private String moneyCount;//交易金额(已完成)"
    @SerializedName("user_name")
    private String userName;//用户名"
    @SerializedName("user_tel")
    private String userPhone;//联系电话"
    @SerializedName("shop_name")
    private String shopName;//商家名称"
    @SerializedName("province_id1")
    private String province;//商家所在省名称"
    @SerializedName("city_id1")
    private String city;//商家所在市名称"
    @SerializedName("qushi1")
    private String district;//商家所在区名称"
    @SerializedName("shop_address")
    private String shopAddress;//商家详细地址",

    public String getUserCount() {
        return userCount;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getMoneyCount() {
        return moneyCount;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getShopName() {
        return shopName;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getShopAddress() {
        return shopAddress;
    }
}
