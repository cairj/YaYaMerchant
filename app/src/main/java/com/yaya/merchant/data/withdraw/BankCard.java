package com.yaya.merchant.data.withdraw;

import java.io.Serializable;

public class BankCard implements Serializable {

     /*"tenantId": 1,
             "bankName": "建行福建分行南靖支行",
             "cardOwner": "虎伯寮",
             "cardType": "公司",
             "cardNo": "1232131232131321305",
             "cardLicence": "http://127.0.0.1:800/Upload/Images//1/1_131620054250515412.jpg",
             "status": "通过",
             "creationTime": "2018-02-07T14:09:40",
             "creatorUserId": null,
             "deleterUserId": 2,
             "deletionTime": "2018-02-25T11:16:53",
             "isDeleted": false,
             "lastModifierUserId": null,
             "lastModificationTime": "2018-02-08T00:49:39",
             "id": 3*/
     private String bankName;
     private String cardNo;

    public String getBankName() {
        return bankName;
    }

    public String getCardNo() {
        return cardNo;
    }
}
