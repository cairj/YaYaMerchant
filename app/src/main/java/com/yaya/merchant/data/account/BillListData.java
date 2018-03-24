package com.yaya.merchant.data.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/24.
 */

public class BillListData implements Serializable {

    private String ordertotal;//2,
    private String realAmount;//2,
    private List<BillData> rows;//

    private int total;
    private int pageCount;

    public String getOrdertotal() {
        return ordertotal;
    }

    public String getRealAmount() {
        return realAmount;
    }

    public List<BillData> getRows() {
        return rows;
    }

    public int getPageCount(int pageSize) {
        if (rows !=null){
            float pageCount=1.0f*total/pageSize;
            return (int) Math.ceil(pageCount);
        }
        return 0;
    }
}
