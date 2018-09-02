package com.yaya.merchant.data.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/9/2.
 */

public class MerchantInfo implements Serializable {

    @SerializedName("shop_name")
    private String shopName;//商家名",
    @SerializedName("company_fuze")
    private String companyFuze;//联系人",
    @SerializedName("company_lianxi")
    private String companyPhone;//联系电话",
    @SerializedName("license_number")
    private String licenseNumber;//营业执照",
    @SerializedName("agent_name")
    private String agentName;//所属的代理名",
    @SerializedName("shop_platform_commission_rate")
    private String shopPlatformCCommissionRate;//线上佣金比例",
    @SerializedName("shop_offline_commission_rate")
    private String shopOfflineCommissionRate;//线下佣金比例",
    @SerializedName("account_contact")
    private String accountContact;//账号联系人",
    private String accounts;//账号",
    private String bank;//开户行",

    public String getShopName() {
        return shopName;
    }

    public String getCompanyFuze() {
        return companyFuze;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getShopPlatformCCommissionRate() {
        return shopPlatformCCommissionRate;
    }

    public String getShopOfflineCommissionRate() {
        return shopOfflineCommissionRate;
    }

    public String getAccountContact() {
        return accountContact;
    }

    public String getAccounts() {
        return accounts;
    }

    public String getBank() {
        return bank;
    }
}
