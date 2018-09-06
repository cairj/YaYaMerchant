package com.yaya.merchant.data.goods;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/9/3.
 */

public class Goods implements Serializable {

    public static final String[] STATUS = {"上架","下架","审核中"};
    public static final String STATUS_PUT_AWAY = "1";
    public static final String STATUS_SOLD_OUT = "0";
    public static final String STATUS_APPLYING = "2";
    public static final String[] STATUS_PARAMS = {STATUS_PUT_AWAY,STATUS_SOLD_OUT,STATUS_APPLYING};

    public static final String APPLY_STATE_APPLY_PASS = "2";
    public static final String APPLY_STATE_APPLYING = "1";
    public static final String APPLY_STATE_NOT_APPLY = "0";

    @SerializedName("goods_id")
    private String goodsId;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("real_sales")
    private String goodsRealSale;//实际销量
    @SerializedName("goods_spec_format")
    private GoodsFormat[] goodsFormat;
    private String stock;
    private String state;
    private String price;
    @SerializedName(value = "meitu",alternate = {"picture"})
    private String picImg;
    @SerializedName("apply_state")
    private String applyState;
    @SerializedName("category_name_1")
    private String category1 = "";
    @SerializedName("category_name_2")
    private String category2 = "";
    @SerializedName("category_name_3")
    private String category3 = "";
    @SerializedName("extend_category_id_1")
    private String extentCategory1 = "";
    @SerializedName("extend_category_id_2")
    private String extentCategory2 = "";
    @SerializedName("extend_category_id_3")
    private String extentCategory3 = "";
    @SerializedName("is_recommend")
    private int isRecommend;
    @SerializedName("max_buy")
    private int maxBuy;

    public String getGoodsId() {
        return goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsRealSale() {
        return goodsRealSale;
    }

    public GoodsFormat[] getGoodsFormat() {
        return goodsFormat;
    }

    public String getStock() {
        return stock;
    }

    public String getState() {
        return state;
    }

    public String getPrice() {
        return price;
    }

    public String getPicImg() {
        return picImg;
    }

    public String getApplyState() {
        return applyState;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setApplyState(String applyState) {
        this.applyState = applyState;
    }

    public String getCategory1() {
        return category1;
    }

    public String getCategory2() {
        return category2;
    }

    public String getCategory3() {
        return category3;
    }

    public String getExtentCategory1() {
        return extentCategory1;
    }

    public String getExtentCategory2() {
        return extentCategory2;
    }

    public String getExtentCategory3() {
        return extentCategory3;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public int getMaxBuy() {
        return maxBuy;
    }

    public class GoodsFormat{

        @SerializedName("value")
        private GoodsFormatValue[] formatValue;

        public class GoodsFormatValue{

            @SerializedName("spec_value_name")
            private String specValueName;

            public String getSpecValueName() {
                return specValueName;
            }
        }

        public GoodsFormatValue[] getFormatValue() {
            return formatValue;
        }
    }
}
