package com.yaya.merchant.data.user;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/16.
 */

public class MerchantData implements Serializable {

    private String name;//门店名称
    private String logo;//Logo
    private String address;//地址
    private String status;//营业状态
    private String storeId;//Id

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public String getStoreId() {
        return storeId;
    }
}
