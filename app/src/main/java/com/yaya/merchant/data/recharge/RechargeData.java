package com.yaya.merchant.data.recharge;

import com.google.gson.annotations.SerializedName;
import com.toroke.okhttp.BaseRowData;

import java.io.Serializable;

/**
 * Created by 蔡蓉婕 on 2018/9/11.
 */

public class RechargeData extends BaseRowData<RechargeData.RechargeRows> {

    private String balance;

    public String getBalance() {
        return balance;
    }

    public class RechargeRows implements Serializable{
        public static final int SIGN_TYPE_RECHARGE = 1;//充值
        public static final int SIGN_TYPE_CONSUME = 0;//消费

        /*private String id;//378,
        private String uid;// 12346,
        private String shop_id;// 16, // 店铺id
        private String account_type;// 2,*/
        private int sign;// 1, // 1 充值 0 消费
        private String number;// "0.06",
        /*private String from_type;// 4,
        private String ata_id;// 84,*/
        private String text;// "微信充值", //
        @SerializedName("create_time")
        private String createTime;// 1533241581, // 创建时间
        @SerializedName("nick_name")
        private String nickName;// "恶魔云", // 昵称
        @SerializedName("user_tel")
        private String phone;//"13860193686", // 手机号码
        @SerializedName("user_email")
        private String userEmail;// "", // 用户邮箱
        @SerializedName("user_headimg")
        private String userHeadImg;// "http:\/\/thirdwx.BQibs4T8kG9wg\/132", // 用户头像
        private String balance;// "0.00",
        @SerializedName("order_no")
        private String orderNo;// 84, // 订单号
        @SerializedName("sign_type")
        private String signType;// "充值" // 订单类型（消费。充值）

        public int getSign() {
            return sign;
        }

        public String getNumber() {
            return number;
        }

        public String getText() {
            return text;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getNickName() {
            return nickName;
        }

        public String getPhone() {
            return phone;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public String getUserHeadImg() {
            return userHeadImg;
        }

        public String getBalance() {
            return balance;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public String getSignType() {
            return signType;
        }
    }

}
