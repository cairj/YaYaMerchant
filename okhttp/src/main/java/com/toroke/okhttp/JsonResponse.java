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
    private T data;

    /** list对象 */
    @SerializedName("results")
    private List<T> dataList = new ArrayList<>();


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
