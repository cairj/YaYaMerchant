package com.yaya.merchant.data.withdraw;

import java.io.Serializable;

/**
 * Created by 蔡蓉婕 on 2018/3/15.
 */

public class MemberBalance implements Serializable {
    private String balance;//账户余额
    private String cashout;//已提现金额
    private String processingAmount;//提现处理中金额
    private String amount;//可提现金额

    public String getBalance() {
        return balance;
    }

    public String getCashout() {
        return cashout;
    }

    public String getProcessingAmount() {
        return processingAmount;
    }

    public String getAmount() {
        return amount;
    }
}
