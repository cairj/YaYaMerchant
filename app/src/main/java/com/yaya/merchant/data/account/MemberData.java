package com.yaya.merchant.data.account;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/31.
 */

public class MemberData implements Serializable{

    private String todayRecharge;// 1,//今日充值（元）
    private String onlineRecharge;// 2,//线上充值
    private String rebirth;// 3,//赠送返现
    private String cashRecharge;// 4,现金充值
    private String todayPrice;// 5,今日消费（元）
    private String todayMember;// 6,今日激活会员（人）
    private String totalRecharge;// 7,会员充值总额（元）
    private String rechargeBalance;// 8,会员充值余额（元）
    private String totalConsumption;// 9,会员消费总额（元）
    private String totalMember;// 10会员总人数（人）

    public String getTodayRecharge() {
        return todayRecharge;
    }

    public String getOnlineRecharge() {
        return onlineRecharge;
    }

    public String getRebirth() {
        return rebirth;
    }

    public String getCashRecharge() {
        return cashRecharge;
    }

    public String getTodayPrice() {
        return todayPrice;
    }

    public String getTodayMember() {
        return todayMember;
    }

    public String getTotalRecharge() {
        return totalRecharge;
    }

    public String getRechargeBalance() {
        return rechargeBalance;
    }

    public String getTotalConsumption() {
        return totalConsumption;
    }

    public String getTotalMember() {
        return totalMember;
    }
}
