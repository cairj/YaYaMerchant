package com.yaya.merchant.data.withdraw;

import com.google.gson.annotations.SerializedName;

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
    public static final String CASH_OUT_STATUS_ALL_VALUE = "";
    public static final String CASH_OUT_STATUS_IN_PROGRESS = "提现中";
    public static final String CASH_OUT_STATUS_IN_PROGRESS_VALUE = "0";
    public static final String CASH_OUT_STATUS_SUCCESS = "提现成功";
    public static final String CASH_OUT_STATUS_SUCCESS_VALUE = "1";
    public static final String CASH_OUT_STATUS_FAILURE = "提现失败";
    public static final String CASH_OUT_STATUS_FAILURE_VALUE = "-1";
    public static final String[] CASH_OUT_STATUS = {CASH_OUT_STATUS_ALL, CASH_OUT_STATUS_SUCCESS,CASH_OUT_STATUS_IN_PROGRESS,
            CASH_OUT_STATUS_FAILURE};
    public static final String[] CASH_OUT_STATUS_VALUES = {CASH_OUT_STATUS_ALL_VALUE,CASH_OUT_STATUS_SUCCESS_VALUE,CASH_OUT_STATUS_IN_PROGRESS_VALUE,
            CASH_OUT_STATUS_FAILURE_VALUE};

    @SerializedName("cash")
    private String amount;//提现金额
    @SerializedName("withdraw_type")
    private String cashoutType;//提现方式
    private String cashoutFee ="0.00";//手续费
    @SerializedName("ask_for_date")
    private String creationTime;//提现时间
    @SerializedName("bank_name")
    private String bankName;//提现银行卡
    @SerializedName("account_number")
    private String accountNumber;//提现银行卡账号
    @SerializedName("withdraw_no")
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

    public String getAccountNumber() {
        return accountNumber;
    }
}
