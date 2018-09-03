package com.yaya.merchant.data.withdraw;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BankCard implements Serializable {

    @SerializedName("account_balance")
    private float accountBalance;
    @SerializedName("can_account_proceeds")
    private float canAccountProceeds;
    @SerializedName("shop_bank_account")
    private BankAccount bankAccount;

    public float getAccountBalance() {
        return accountBalance;
    }

    public float getCanAccountProceeds() {
        return canAccountProceeds;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public class BankAccount implements Serializable{
        private int id;
        @SerializedName("shop_id")
        private int shopId;
        @SerializedName("branch_bank_name")
        private String bankName;
        @SerializedName("bank_type")
        private String bankType;
        @SerializedName("account_number")
        private String accountNumber;
        @SerializedName("is_default")
        private int isDefault;
        @SerializedName("realname")
        private String realName;

        public int getId() {
            return id;
        }

        public int getShopId() {
            return shopId;
        }

        public String getBankName() {
            return bankName;
        }

        public String getBankType() {
            return bankType;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public String getRealName() {
            return realName;
        }
    }
}
