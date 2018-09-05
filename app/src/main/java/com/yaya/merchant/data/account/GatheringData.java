package com.yaya.merchant.data.account;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/31.
 */

public class GatheringData implements Serializable {

    @SerializedName("pay_money")
    private String orderSumprice;//消费金额
    @SerializedName("order_money")
    private String orderPrice;//订单金额
    private String balancePrice;//余额
    @SerializedName("shop_name")
    private String storeName;//收款门店
    @SerializedName("create_time")
    private String creationTime;//创建时间
    @SerializedName("pay_time")
    private String payTime;//支付时间
    @SerializedName("pay_type")
    private String payType;
    @SerializedName("type")
    private String orderType;//交易类型
    private String orderSn;//订单号
    private String refundAmount;//退款金额
    private String refundState;//退款状态
    private String refundTime;//退款时间
    @SerializedName("payNmae")
    private String payName;//付款顾客
    @SerializedName("out_trade_no")
    private String tradeNo;//商户编号
    @SerializedName("nick_name")
    private String name;//昵称
    @SerializedName("buyer_message")
    private String remark;
    @SerializedName("user_headimg")
    private String headImg;

    public String getOrderSumprice() {
        return orderSumprice;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public String getBalancePrice() {
        return balancePrice;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public String getRefundState() {
        return refundState;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public String getPayName() {
        return payName;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public String getName() {
        return name;
    }

    public String getPayType() {
        return payType;
    }

    public String getRemark() {
        return remark;
    }

    public String getHeadImg() {
        return headImg;
    }
}
