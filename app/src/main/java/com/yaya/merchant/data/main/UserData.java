package com.yaya.merchant.data.main;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/16.
 */

public class UserData implements Serializable {
    @SerializedName("user_headimg")
    private String logo;//头像
    @SerializedName("user_name")
    private String name;//管理员名称

    @SerializedName("user_tel")
    private String phone;
    @SerializedName("shop_company_name")
    private String companyName;
    @SerializedName("agent_name")
    private String agentName;
    @SerializedName("reg_time")
    private String regTime;


    public String getHeadImgUrl() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getRegTime() {
        return regTime;
    }
}
