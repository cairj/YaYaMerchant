package com.yaya.merchant.data.user;

import com.yaya.merchant.data.order.OrderDetailData;

import java.io.Serializable;
import java.util.List;

/**
 */

public class VerificationInfo implements Serializable {

    private String orderSn;//2018082113470001", // 订单号
    private String payStatus;//已支付",
    private String orderStatus;//待发货",
    private String payTime;//2018-08-21T14:19:21", // 付款时间
    private String type;//买家自提",
    private String userPhone;//15055055011",
    private String userName;//Jun",
    private String userAddress;//海南省海口市琼山区45464564",
    private String memberInfo;//Jun:15055055011",
    private String creationTime;//2018-08-21T13:47:49",
    private String paySource;//微信支付",
    private String orderPrice;//0.01",
    private String deliverPrice;//0.00",
    private String totalPrice;//0.01", // 商品总价
    private String order_id;//8639, // order_id
    private int status;// 1,  // 不等1为核销过了
    private List<OrderDetailData> orderdetail;// [{ // 商品列表

    public String getOrderSn() {
        return orderSn;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
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

    public String getUserAddress() {
        return userAddress;
    }

    public String getMemberInfo() {
        return memberInfo;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public String getPaySource() {
        return paySource;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public String getDeliverPrice() {
        return deliverPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getOrder_id() {
        return order_id;
    }

    public int getStatus() {
        return status;
    }

    public List<OrderDetailData> getOrderdetail() {
        return orderdetail;
    }
}
