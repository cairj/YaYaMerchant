package com.yaya.merchant.data.login;

import com.toroke.okhttp.BaseData;

/**
 * Created by admin on 2018/3/5.
 */

public class SendMessageData extends BaseData {

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
