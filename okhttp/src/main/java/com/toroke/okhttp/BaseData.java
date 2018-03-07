package com.toroke.okhttp;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/4.
 */

public class BaseData<T extends Serializable> implements Serializable {

    private T data;
    private boolean status;
    private String userId;

    public boolean isStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getUserId() {
        return userId;
    }
}
