package com.yaya.merchant.data.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/16.
 */

public class MerchantData implements Serializable {


    //门店状态：0：待营业，1：营业中，2：已关闭
    public static final String OPEARTING_STATUS_ALL="";//全部状态
    public static final String OPEARTING_STATUS_OFF="0";//已关闭
    public static final String OPEARTING_STATUS_ON="1";//开启中
    public static final String[] OPEARTING_STATUS={"全部状态","开启中","关闭"};
    public static final String[] OPEARTING_STATUS_ID={OPEARTING_STATUS_ALL,OPEARTING_STATUS_ON,OPEARTING_STATUS_OFF};

    private String shop_id;//商家id
    private String shop_name;//商家名称
    private String store_name;//门店名称
    private String store_address;//ic_merchant_address
    private String status;//营业状态
    private String store_id;//门店Id
    private String shop_company_name;//公司名
    private String shop_group_ids;//分类名称
    @SerializedName(value = "nen_logos",alternate = {"logos"})
    private String logo;
    private String url;//图片二维码地址

    public String getName() {
        return store_name;
    }

    public String getAddress() {
        return store_address;
    }

    public String getStatus() {
        return status;
    }

    public String getStoreId() {
        return store_id;
    }

    public String getUrl() {
        return url;
    }

    public String getShopName() {
        return shop_name;
    }

    public String getShopId() {
        return shop_id;
    }

    public String getShopCompanyName() {
        return shop_company_name;
    }

    public String getShopGroupIds() {
        return shop_group_ids;
    }

    public String getLogo() {
        return logo;
    }
}
