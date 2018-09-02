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
    private BaseData<T> result;

    private T data;
    private int code;
    private String msg;
    private List<T> dataList;

    public BaseData<T> getData() {
        return result;
    }

    public void setData(BaseData<T> data) {
        this.result = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getResultData(){
        return data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BaseData<T> getResult() {
        return result;
    }

    public void setResult(BaseData<T> result) {
        this.result = result;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
