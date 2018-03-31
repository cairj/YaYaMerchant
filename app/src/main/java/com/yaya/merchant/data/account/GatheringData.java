package com.yaya.merchant.data.account;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/31.
 */

public class GatheringData implements Serializable {

    private String orderSumprice;//消费金额
    private String orderPrice;//订单金额
    private String balancePrice;//余额
    private String storeName;//收款门店
    private String creationTime;//创建时间
    private String payTime;//支付时间
    @SerializedName(value = "payType",alternate = {"orderType"})
    private String orderType;//交易类型
    private String orderSn;//订单号
    private String refundAmount;//退款金额
    private String refundState;//退款状态
    private String refundTime;//退款时间
    @SerializedName("payNmae")
    private String payName;//付款顾客
    private String tradeNo;//商户编号
    private String name;//微信昵称

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
}
