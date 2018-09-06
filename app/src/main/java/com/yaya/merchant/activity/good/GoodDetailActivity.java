package com.yaya.merchant.activity.good;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.GoodsAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.goods.Goods;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.UserHelper;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/9/6.
 */

public class GoodDetailActivity extends BaseActivity{

    private String goodsId;

    private Goods goods;

    @BindView(R.id.iv_goods_pic)
    protected ImageView goodsPicIv;
    @BindView(R.id.tv_goods_price)
    protected TextView goodsPriceTv;
    @BindView(R.id.tv_goods_stock)
    protected TextView goodsStockTv;
    @BindView(R.id.tv_goods_real_sale)
    protected TextView goodsRealSaleTv;
    @BindView(R.id.tv_change_status)
    protected TextView changeStatusTv;
    @BindView(R.id.tv_goods_name)
    protected TextView goodsNameTv;
    @BindView(R.id.tv_goods_freight)
    protected TextView goodsFreightTv;
    @BindView(R.id.tv_goods_category)
    protected TextView goodsCategoryTv;
    @BindView(R.id.tv_goods_extend_category)
    protected TextView goodsExtentCategoryTv;
    @BindView(R.id.tv_goods_max_buy)
    protected TextView goodsMaxBuyTv;
    @BindView(R.id.tv_goods_recommend)
    protected TextView goodsRecommendTv;

    public static void open(Context context,String goodsId){
        Intent intent = new Intent(context,GoodDetailActivity.class);
        intent.putExtra("goodsId",goodsId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        goodsId = getIntent().getStringExtra("goodsId");
        GoodsAction.getGoodsDetail(goodsId, new GsonCallback<Goods>(Goods.class) {
            @Override
            public void onSucceed(JsonResponse<Goods> response) {
                goods = response.getResultData();
                GlideLoaderHelper.loadImg(goods.getPicImg(),goodsPicIv);
                goodsPriceTv.setText("￥"+goods.getPrice());
                goodsStockTv.setText("库存："+goods.getStock());
                goodsRealSaleTv.setText("销售："+goods.getGoodsRealSale());
                goodsFreightTv.setText("运费：");
                goodsMaxBuyTv.setText("每人限购：" + ((goods.getMaxBuy() == 0) ? "不限" : goods.getMaxBuy()));
                goodsRecommendTv.setText("首页推荐：" + ((goods.getIsRecommend() == 0) ? "不推荐" : "推荐"));

                if (TextUtils.isEmpty(goods.getCategory1()) && TextUtils.isEmpty(goods.getCategory2())
                        && TextUtils.isEmpty(goods.getCategory3())) {
                    goodsCategoryTv.setText("分类：无");
                } else {
                    goodsCategoryTv.setText("分类：" + goods.getCategory1() + " " + goods.getCategory2() + " " + goods.getCategory3());
                }

                if (TextUtils.isEmpty(goods.getExtentCategory1()) && TextUtils.isEmpty(goods.getExtentCategory2())
                        && TextUtils.isEmpty(goods.getExtentCategory3())) {
                    goodsExtentCategoryTv.setText("扩展分类：无");
                } else {
                    goodsExtentCategoryTv.setText("扩展分类：" + goods.getExtentCategory1() + " "
                            + goods.getExtentCategory2() + " " + goods.getExtentCategory3());
                }

                goodsNameTv.setText(goods.getGoodsName());

                fillGoodsStatus(goods);
            }
        });
    }

    private void fillGoodsStatus(Goods goods){
        if (Goods.STATUS_SOLD_OUT.equals(goods.getState()) && !Goods.APPLY_STATE_APPLYING.equals(goods.getApplyState())) {
            changeStatusTv.setText("上架");
            changeStatusTv.setEnabled(true);
            changeStatusTv.setSelected(false);
            changeStatusTv.setTextColor(ContextCompat.getColor(this,R.color.white));
        }

        if (Goods.STATUS_PUT_AWAY.equals(goods.getState())) {
            changeStatusTv.setText("下架");
            changeStatusTv.setEnabled(true);
            changeStatusTv.setSelected(true);
            changeStatusTv.setTextColor(ContextCompat.getColor(this,R.color.red_f94745));
        }

        if (Goods.STATUS_SOLD_OUT.equals(goods.getState()) && Goods.APPLY_STATE_APPLYING.equals(goods.getApplyState())) {
            if (UserHelper.isAgent()){
                changeStatusTv.setEnabled(true);
                changeStatusTv.setText("上架");
            }
            if (UserHelper.isMerchant()) {
                changeStatusTv.setEnabled(false);
                changeStatusTv.setText("审核中");
            }
            changeStatusTv.setSelected(false);
            changeStatusTv.setTextColor(ContextCompat.getColor(this,R.color.white));
        }
    }

    @OnClick(R.id.tv_change_status)
    protected void changeStatus(){
        GsonCallback<Goods> callback = new GsonCallback<Goods>(Goods.class) {
            @Override
            public void onSucceed(JsonResponse<Goods> response) {
                goods.setApplyState(response.getResultData().getApplyState());
                goods.setState(response.getResultData().getState());
                fillGoodsStatus(goods);
            }
        };
        if (Goods.STATUS_SOLD_OUT.equals(goods.getState()) && Goods.APPLY_STATE_NOT_APPLY.equals(goods.getApplyState())) {
            GoodsAction.changeGoodState(Goods.STATUS_SOLD_OUT,goodsId,callback);
        }

        if (Goods.STATUS_PUT_AWAY.equals(goods.getState())) {
            GoodsAction.changeGoodState(Goods.STATUS_PUT_AWAY,goodsId,callback);
        }

        if (Goods.STATUS_SOLD_OUT.equals(goods.getState()) && Goods.APPLY_STATE_APPLYING.equals(goods.getApplyState())) {
            GoodsAction.changeGoodState(Goods.STATUS_APPLYING,goodsId,callback);
        }
    }
}
