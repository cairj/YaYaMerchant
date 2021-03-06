package com.toroke.okhttp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 存放返回的json数据
 */
public class JsonResponse<T extends Serializable> implements Serializable{

    /** 单个对象 */
    @SerializedName("result")
    private BaseData<T> data;

    private ErrorData error;
    private boolean success;

    public BaseData<T> getData() {
        return data;
    }

    public void setData(BaseData<T> data) {
        this.data = data;
    }

    public ErrorData getError() {
        return error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setError(ErrorData error) {
        this.error = error;
    }
}
