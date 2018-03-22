package com.yaya.merchant.data.user;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/16.
 */

public class MerchantData implements Serializable {


    //门店状态：0：待营业，1：营业中，2：已关闭
    public static final String OPEARTING_STATUS_ALL="";//全部状态
    public static final String OPEARTING_STATUS_PREPARE="0";//待营业
    public static final String OPEARTING_STATUS_ON="1";//营业中
    public static final String OPEARTING_STATUS_OFF="2";//已关闭
    public static final String[] OPEARTING_STATUS={"全部状态","待营业","营业中","已关闭"};
    public static final String[] OPEARTING_STATUS_ID={OPEARTING_STATUS_ALL,OPEARTING_STATUS_PREPARE,OPEARTING_STATUS_ON,OPEARTING_STATUS_OFF};

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
