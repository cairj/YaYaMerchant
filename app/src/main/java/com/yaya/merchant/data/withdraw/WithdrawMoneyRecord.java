package com.yaya.merchant.data.withdraw;

import java.io.Serializable;

/**
 * Created by 蔡蓉婕 on 2018/3/15.
 */

public class WithdrawMoneyRecord implements Serializable{

    private String amount;//提现金额
    private String cashoutType;//提现方式
    private String cashoutFee;//手续费
    private String creationTime;//提现时间
    private String bankName;//提现银行卡
    private String cashoutNo;//流水号
    private String status;//提现状态

    public String getAmount() {
        return amount;
    }

    public String getCashoutType() {
        return cashoutType;
    }

    public String getCashoutFee() {
        return cashoutFee;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCashoutNo() {
        return cashoutNo;
    }

    public String getStatus() {
        return status;
    }
}
