package com.yaya.merchant.data.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/25.
 */

public class ExpressCompany implements Serializable {

    @SerializedName("company_name")
    private String expressName;
    @SerializedName("express_no")
    private String expressNo;
    private boolean isSelect;

    public String getExpressName() {
        return expressName;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
