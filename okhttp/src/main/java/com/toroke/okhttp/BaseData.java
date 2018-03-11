package com.toroke.okhttp;

import java.io.Serializable;
import java.util.List;

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

    public void setData(T data) {
        this.data = data;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
