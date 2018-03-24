package com.yaya.merchant.data.account;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/24.
 */

public class BalanceAccount implements Serializable {

    private String orderPrice;//订单金额
    private String feeTotal;//手续费
    private String refundMoneyTotal;//净退款
    private String realAmount;//实际到账
    private String collectionAmount;//商家实收金额
    private String incomeAmount;//商家净收金额
    private String ordertotal;//订单笔数
    private String refundCount;//退款笔数

    public String getOrderPrice() {
        return orderPrice;
    }

    public String getFeeTotal() {
        return feeTotal;
    }

    public String getRefundMoneyTotal() {
        return refundMoneyTotal;
    }

    public String getRealAmount() {
        return realAmount;
    }

    public String getCollectionAmount() {
        return collectionAmount;
    }

    public String getIncomeAmount() {
        return incomeAmount;
    }

    public String getOrdertotal() {
        return ordertotal;
    }

    public String getRefundCount() {
        return refundCount;
    }
}
