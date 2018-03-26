package com.yaya.merchant.data.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/25.
 */

public class OrderDetail implements Serializable {

    public static final int TYPE_ORDER_LIST=1;
    public static final int TYPE_DELIVER_ORDER_LIST=2;
    public static final int TYPE_REFUND_ORDER_LIST=3;

    private String id;//"68", //发货设置的参数
    private String memberId;// "1", //发货设置的参数
    private String orderSn;// "2018031916095000032118",
    private String payStatus;// "已付款",
    private String orderStatus;//"待发货"，
    private String payTime;// null, //支付时间
    private String type;// "用户自提",//配送方式
    private String userPhone;// "13959830580",
    private String userName;// "孙悟空",
    private String userAddress;// "花果山水帘洞",
    private String creationTime;//"2018-03-19T16:09:53",//下单时间
    private String paySource;// "微信支付",//支付方式
    private String orderPrice;//1700,//商品总额
    private String deliverPrice;// 0,//运费
    private String totalPrice;// 1700,//实付款
    private String refundReason;// 退货原因
    private String memberInfo;// 退货原因
    private List<OrderDetailData> orderdetail;//

    public String getId() {
        return id;
    }

    public String getMemberId() {
        return memberId;
    }

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

    public String getRefundReason() {
        return refundReason;
    }

    public String getMemberInfo() {
        return memberInfo;
    }

    public List<OrderDetailData> getOrderdetail() {
        return orderdetail;
    }
}
