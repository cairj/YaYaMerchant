package com.yaya.merchant.data.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 蔡蓉婕 on 2018/9/17.
 */

public class RefundOrderDetail implements Serializable {

    @SerializedName("refund_balance")
    private String refundBalance;
    private String remark;
    @SerializedName("order_goods")
    private OrderDetail orderGoods;
    @SerializedName("order_info")
    private OrderInfo orderInfo;
    private Treasure treasure;
    @SerializedName("call_center")
    private ServiceIdel serviceIdel;

    public String getRefundBalance() {
        return refundBalance;
    }

    public String getRemark() {
        return remark;
    }

    public OrderDetail getOrderGoods() {
        return orderGoods;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public Treasure getTreasure() {
        return treasure;
    }

    public ServiceIdel getServiceIdel() {
        return serviceIdel;
    }

    public class OrderInfo{
        @SerializedName("payment_type")
        private String paymentType;
        @SerializedName("shipping_type")
        private String shippingType;
        @SerializedName("create_time")
        private String createTime;
        @SerializedName("order_no")
        private String orderNumber;

        public String getPaymentType() {
            return paymentType;
        }

        public String getShippingType() {
            return shippingType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getOrderNumber() {
            return orderNumber;
        }
    }

    public class Treasure{//财务审核 状态为5 退款成功时显示
        private String opinion;
        private String remark;
        private String status;

        public String getOpinion() {
            return opinion;
        }

        public String getRemark() {
            return remark;
        }

        public String getStatus() {
            return status;
        }
    }

    public class ServiceIdel{
        private String opinion;
        private String remark;

        public String getOpinion() {
            return opinion;
        }

        public String getRemark() {
            return remark;
        }
    }

}
