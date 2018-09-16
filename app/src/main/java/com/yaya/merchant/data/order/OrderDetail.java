package com.yaya.merchant.data.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/3/25.
 */

public class OrderDetail implements Serializable {

    public static final String TYPE_ORDER_LIST= "";
    public static final String TYPE_DELIVER_ORDER_LIST="1";//待发货
    public static final String TYPE_REFUND_ORDER_LIST="2";//已发货

    public static final int ORDER_PAYMENT_TYPE_SCAN = 1;//扫码
    public static final int ORDER_PAYMENT_TYPE_ONLINE = 0;//线上

    public static final String REFUND_ORDER_STATUS_APPLYING = "1";
    public static final String REFUND_ORDER_STATUS_WAITING = "4";
    public static final String REFUND_ORDER_STATUS_SUCCESS = "5";
    public static final String REFUND_ORDER_STATUS_FAIL = "-3";

    private String id;//"68", //发货设置的参数
    private String orderId;//"68", //发货设置的参数
    private String memberId;// "1", //发货设置的参数
    private String orderSn;// "2018031916095000032118",
    @SerializedName(value = "payStatus",alternate = {"status_name"})
    private String payStatus;// "已付款",
    private String orderStatus;//"待发货"，
    private String payTime;// null, //支付时间
    private String type;// "用户自提",//配送方式
    private String userPhone;// "13959830580",
    private String userName;// "孙悟空",
    private String userAddress;// "花果山水帘洞",
    private String creationTime;//"2018-03-19T16:09:53",//下单时间
    private String paySource;// "sign_ic_wechat",//支付方式
    private float orderPrice;//1700,//商品总额
    private float deliverPrice;// 0,//运费
    @SerializedName(value = "totalPrice",alternate = {"pay_money"})
    private String totalPrice;// 1700,//实付款
    @SerializedName("refund_reason")
    private String refundReason;// 退货原因
    private String memberInfo;
    private List<OrderDetailData> orderdetail = new ArrayList<>();//
    @SerializedName("audit_status")
    private String auditStatus;

    @SerializedName("goods_id")
    private String goodsId;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("img")
    private String goodsPic;
    @SerializedName("num")
    private int goodsCount;
    @SerializedName("price")
    private double goodsPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
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

    public float getOrderPrice() {
        return orderPrice;
    }

    public float getDeliverPrice() {
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

    public String getAuditStatus() {
        return auditStatus;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }
}
