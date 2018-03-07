package com.yaya.merchant.data.login;

import com.toroke.okhttp.BaseData;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/5.
 */

public class SendMessageData implements Serializable {

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
