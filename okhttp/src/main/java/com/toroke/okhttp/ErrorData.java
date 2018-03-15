package com.toroke.okhttp;

/**
 * Created by 蔡蓉婕 on 2018/3/15.
 */

public class ErrorData {

    private String code;//0,
    private String message;//登录失败!",
    private String details;//用户名或密码无效",

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
