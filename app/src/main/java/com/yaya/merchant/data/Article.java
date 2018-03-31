package com.yaya.merchant.data;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/31.
 */

public class Article implements Serializable {

    public static final String TYPE_NOTIFY="0";//通知
    public static final String TYPE_INFORMATION="1";//资讯

    private String title;//测试标题",
    private String desciption;//"简介",
    private String imgUrl;// "图片",
    private String creationTime;// "2018-03-29 17:08:07",
    private String id;// 1

    public String getTitle() {
        return title;
    }

    public String getDesciption() {
        return desciption;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public String getId() {
        return id;
    }
}
