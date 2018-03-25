package com.yaya.merchant.data.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/25.
 */

public class OrderData implements Serializable {

    public static final int TYPE_ORDER_LIST=1;
    public static final int TYPE_DELIVER_ORDER_LIST=2;
    public static final int TYPE_REFUND_ORDER_LIST=3;

    private String orderSn;//2018031916094000012095", //订单号
    private String payStatus;//已付款",
    private String payTime;//null,  //支付时间
    private String type;//用户自提", //配送方式
    private String userPhone;//"13959830580", //手机
    private String userName;//孙悟空", //平台用户
    private String refundReason;//退货理由
    private List<OrderDetailData> orderdetail;//

    public String getOrderSn() {
        return orderSn;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public String getPayTime() {
        return payTime;
    }

    public String getType() {
        return type;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public List<OrderDetailData> getOrderdetail() {
        return orderdetail;
    }
}
