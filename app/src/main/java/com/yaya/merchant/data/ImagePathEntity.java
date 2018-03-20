package com.yaya.merchant.data;

import com.toroke.okhttp.BaseData;
import com.toroke.okhttp.JsonResponse;

import java.io.Serializable;

/**
 * 获取图片
 */
public class ImagePathEntity implements Serializable{
    public ImagePathData result;
    public class ImagePathData{
        public String filename;
        public String msg;
    }
}
