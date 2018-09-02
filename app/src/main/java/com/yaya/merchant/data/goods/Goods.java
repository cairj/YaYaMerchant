package com.yaya.merchant.data.goods;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/9/3.
 */

public class Goods implements Serializable {

    @SerializedName("goods_id")
    private String goodsId;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("real_sales")
    private String goodsRealSale;

    public String getGoodsId() {
        return goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsRealSale() {
        return goodsRealSale;
    }
}
