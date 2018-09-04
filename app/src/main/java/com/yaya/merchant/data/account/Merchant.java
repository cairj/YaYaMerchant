package com.yaya.merchant.data.account;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/11.
 */

public class Merchant implements Serializable {

    @SerializedName("store_name")
    private String storeName;
    @SerializedName("store_id")
    private int id;
    private boolean isSelected;
    private boolean isVoice;

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

    public boolean isVoice() {
        return isVoice;
    }
}
