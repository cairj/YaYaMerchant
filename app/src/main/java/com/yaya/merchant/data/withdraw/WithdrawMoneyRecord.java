package com.yaya.merchant.data.withdraw;

import java.io.Serializable;

/**
 * Created by 蔡蓉婕 on 2018/3/15.
 */

public class WithdrawMoneyRecord implements Serializable {

    public static final String CASH_OUT_TYPE_ALL = "";
    public static final String CASH_OUT_TYPE_NORMAL = "普通提现";
    public static final String CASH_OUT_TYPE_HURRY = "快速提现";
    public static final String[] CASH_OUT_TYPE = {CASH_OUT_TYPE_ALL, CASH_OUT_TYPE_NORMAL, CASH_OUT_TYPE_HURRY};

    public static final String CASH_OUT_STATUS_ALL = "";
    public static final String CASH_OUT_STATUS_IN_PROGRESS = "处理中";
    public static final String CASH_OUT_STATUS_SUCCESS = "提现成功";
    public static final String CASH_OUT_STATUS_FAILURE = "提现失败";
    public static final String[] CASH_OUT_STATUS = {CASH_OUT_STATUS_ALL, CASH_OUT_STATUS_IN_PROGRESS,
            CASH_OUT_STATUS_SUCCESS,CASH_OUT_STATUS_FAILURE};

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
