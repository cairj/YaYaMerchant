package com.yaya.merchant.data.account;

import com.toroke.okhttp.BaseRowData;

/**
 * Created by admin on 2018/3/24.
 */

public class BillListData extends BaseRowData<BillDetailData> {

    private String ordertotal;//2,
    private String realAmount;//2,

    public String getOrdertotal() {
        return ordertotal;
    }

    public String getRealAmount() {
        return realAmount;
    }

    /*private List<BillDetailData> rows;//

    private int total;
    private int pageCount;

    public List<BillDetailData> getRows() {
        return rows;
    }

    public int getPageCount(int pageSize) {
        if (rows !=null){
            float pageCount=1.0f*total/pageSize;
            return (int) Math.ceil(pageCount);
        }
        return 0;
    }*/
}
