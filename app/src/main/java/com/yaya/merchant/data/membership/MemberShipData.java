package com.yaya.merchant.data.membership;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 蔡蓉婕 on 2018/10/11.
 */

public class MemberShipData implements Serializable {

    private String id;
    private String balance;
    @SerializedName("user_tel")
    private String userTel;
    @SerializedName("level_name")
    private String levelName;
    @SerializedName("user_headimg")
    private String userHeadImg;
    @SerializedName("user_name")
    private String userName;

    public String getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }

    public String getLevelName() {
        return levelName;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public String getUserTel() {
        return userTel;
    }

    public String getUserName() {
        return userName;
    }
}
