package com.yaya.merchant.data.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/25.
 */

public class OrderDetailData implements Serializable {

    private String img;//123.png //图片
    private int orderNum;//3, //数量
    private double orderPrice;//200, 单价  总价=数量*单价
    private String productName;//"产品3", //产品名称
    private String specName;// "白色，xxl", //规格
    private String orderRemarks;//"订单备注3", //备注

    @SerializedName("order_goods_id")
    private String id;

    public String getImg() {
        return img;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public String getProductName() {
        return productName;
    }

    public String getSpecName() {
        return specName;
    }

    public String getOrderRemarks() {
        return orderRemarks;
    }

    public String getId() {
        return id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public void setOrderRemarks(String orderRemarks) {
        this.orderRemarks = orderRemarks;
    }

    public void setId(String id) {
        this.id = id;
    }
}
