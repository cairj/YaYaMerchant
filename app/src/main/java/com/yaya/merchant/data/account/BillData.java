package com.yaya.merchant.data.account;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 2018/9/4.
 */

public class BillData implements Serializable {

    private String date;
    private int count;
    @SerializedName("data")
    private ArrayList<BillDetailData> rows = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<BillDetailData> getRows() {
        return rows;
    }

    public void setRows(ArrayList<BillDetailData> rows) {
        this.rows = rows;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
