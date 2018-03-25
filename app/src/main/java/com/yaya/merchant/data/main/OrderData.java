package com.yaya.merchant.data.main;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/25.
 */

public class OrderData implements Serializable {

    private String pricetotal;//今日订单总额
    private String orderprice;//已下单,待发货
    private String waitReceivePrice;//待收货
    private String refundPrice;//退单
    private String waitReceiveCount;//待发货数量
    private String refundCount;//审核退单数量
    private String orderCount;//订单数量

    public String getPricetotal() {
        return pricetotal;
    }

    public String getOrderprice() {
        return orderprice;
    }

    public String getWaitReceivePrice() {
        return waitReceivePrice;
    }

    public String getRefundPrice() {
        return refundPrice;
    }

    public String getWaitReceiveCount() {
        return waitReceiveCount;
    }

    public String getRefundCount() {
        return refundCount;
    }

    public String getOrderCount() {
        return orderCount;
    }
}
