package com.yaya.merchant.data.user;

import java.io.Serializable;

/**
 */

public class VerificationInfo implements Serializable {

    private String img;
    private String orderSn;//2018030710390200022424",
    private String payTime;//null,
    private String payStatus;//未付款",
    private String productName;//产品2",
    private String orderPrice;//120,
    private String orderNum;// 2，
    private String verificationSn;// 核销码

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getVerificationSn() {
        return verificationSn;
    }

    public void setVerificationSn(String verificationSn) {
        this.verificationSn = verificationSn;
    }
}
