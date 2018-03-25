package com.yaya.merchant.data.order;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/25.
 */

public class ExpressCompany implements Serializable {

    private String expressName;
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
