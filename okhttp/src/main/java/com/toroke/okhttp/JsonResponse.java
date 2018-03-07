package com.toroke.okhttp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 魏新智 on 2016/5/17.
 * 存放返回的json数据
 */
public class JsonResponse<T extends Serializable> implements Serializable{

    /** 单个对象 */
    @SerializedName("result")
    private BaseData<T> data;

    public BaseData<T> getData() {
        return data;
    }

    public void setData(BaseData<T> data) {
        this.data = data;
    }

}
