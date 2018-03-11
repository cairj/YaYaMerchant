package com.yaya.merchant.data.account;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/11.
 */

public class Merchant implements Serializable {

    private String storeName;
    private int id;
    private boolean isSelected;

    public String getStoreName() {
        return storeName;
    }

    public int getId() {
        return id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
