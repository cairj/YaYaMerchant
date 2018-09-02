package com.yaya.merchant.data.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/9/2.
 */

public class MerchantClassic implements Serializable {

    @SerializedName("shop_group_id")
    private String shopGroupId;//
    @SerializedName("group_name")
    private String groupName;//

    public String getShopGroupId() {
        return shopGroupId;
    }

    public String getGroupName() {
        return groupName;
    }
}
