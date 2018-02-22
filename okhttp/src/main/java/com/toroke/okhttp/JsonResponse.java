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

    public static final int STATUS_SUCCEED = 200;

    /** 状态码 */
    @SerializedName("status")
    private int code;

    /** 错误信息 */
    @SerializedName("message")
    private String msg;

    /** 单个对象 */
    @SerializedName("result")
    private T data;

    /** list对象 */
    @SerializedName("results")
    private List<T> dataList = new ArrayList<>();

    /** 行号 */
    @SerializedName("rownum")
    private int rowNum;

    /** 数量 */
    private int count;

    private PageInfo pageInfo;

    private long timestamp;

    /** 错误信息 */
    private String tip;

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

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
