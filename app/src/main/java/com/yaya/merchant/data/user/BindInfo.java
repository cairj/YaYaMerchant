package com.yaya.merchant.data.user;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/17.
 */

public class BindInfo implements Serializable {

    private UserInfo userInfo;//公司法人信息对象
    private BankInfo bankInfo;//银行卡信息对象，如果是空为已解绑

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public BankInfo getBankInfo() {
        return bankInfo;
    }

    public class UserInfo{
        private String name;//公司名称
        private String businessLicenseNo;//营业执照
        private String corporation;//企业法人
        private String cardId;//身份证

        public String getName() {
            return name;
        }

        public String getBusinessLicenseNo() {
            return businessLicenseNo;
        }

        public String getCorporation() {
            return corporation;
        }

        public String getCardId() {
            return cardId;
        }
    }

    public class BankInfo{
        private String id;//解绑所需id
        private String bankName;//银行卡名称
        private String cardNo;//银行卡号

        public String getId() {
            return id;
        }

        public String getBankName() {
            return bankName;
        }

        public String getCardNo() {
            return cardNo;
        }
    }

}
