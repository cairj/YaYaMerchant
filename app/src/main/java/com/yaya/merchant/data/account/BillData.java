package com.yaya.merchant.data.account;

import java.io.Serializable;

/**
 * 入账数据
 */

public class BillData implements Serializable {

    /*payState付款状态对应值*/
    public static final String[] PAY_STATE = {"全部状态", "已付款", "待付款", "全额退款", "部分退款", "已关闭", "已撤销"};
    public static final String PAY_STATE_ALL = "";//全部
    public static final String PAY_STATE_PAID = "1";//已支付
    public static final String PAY_PENDING_PAID = "2";//待付款
    public static final String PAY_STATE_FULL_REFUND = "3";//全额退款
    public static final String PAY_STATE_REBATES = "4";//部分退款
    public static final String PAY_STATE_CLOSED = "5";//已关闭
    public static final String PAY_STATE_CANCELLED = "6";//已撤销
    public static final String[] PAY_STATE_PARAMS = {PAY_STATE_ALL, PAY_STATE_PAID, PAY_PENDING_PAID, PAY_STATE_FULL_REFUND,
            PAY_STATE_REBATES, PAY_STATE_CLOSED, PAY_STATE_CANCELLED};

    /*payType支付方式*/
    public static final String PAY_TYPE_ALL = "";//全部
    public static final String PAY_TYPE_WECHAT = "1";//微信支付
    public static final String PAY_TYPE_ALIPAY = "2";//支付宝支付

    /*订单类型  线上充值，现金充值，会员消费*/
    public static final String ORDER_TYPE_ALL = "";//全部
    public static final String ORDER_TYPE_ONLINE = "线上充值";//线上充值
    public static final String ORDER_TYPE_CASH = "现金充值";//现金充值
    public static final String ORDER_TYPE_VIP = "会员消费";//会员消费
    public static final String[] ORDER_TYPE = {ORDER_TYPE_ALL, ORDER_TYPE_ONLINE, ORDER_TYPE_CASH, ORDER_TYPE_VIP}  ;

    private String headImgUrl;//头像
    private String payType;//支付方式
    private float orderSumprice;//实付;
    private String payStatus;//是否付款
    private String payTime;//付款时间
    private int id;//用作收款详情的参数
    private String name;//名称
    private String orderType;//订单类型

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public String getPayType() {
        return payType;
    }

    public float getOrderSumprice() {
        return orderSumprice;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public String getPayTime() {
        return payTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrderType() {
        return orderType;
    }
}
