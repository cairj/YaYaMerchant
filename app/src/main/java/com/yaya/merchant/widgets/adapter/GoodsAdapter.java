package com.yaya.merchant.widgets.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.goods.Goods;
import com.yaya.merchant.util.UserHelper;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import java.util.List;

/**
 * Created by 蔡蓉婕 on 2018/9/5.
 */

public class GoodsAdapter extends BaseQuickAdapter<Goods> {
    public GoodsAdapter(List<Goods> data) {
        super(R.layout.item_goods,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final Goods goods) {
        baseViewHolder.setText(R.id.tv_goods_name,goods.getGoodsName())
                .setText(R.id.tv_goods_price,goods.getPrice())
                .setText(R.id.tv_goods_stock,goods.getGoodsRealSale()+"/"+goods.getStock());

        GlideLoaderHelper.loadImg(goods.getPicImg(), (ImageView) baseViewHolder.getView(R.id.iv_goods_pic));

        String goodsFormat = "";
        for (int i = 0; i < goods.getGoodsFormat().length; i++) {
            for (int j = 0; j < goods.getGoodsFormat()[i].getFormatValue().length; j++) {
                goodsFormat += goods.getGoodsFormat()[i].getFormatValue()[j].getSpecValueName() + ",";
            }
        }
        if (goodsFormat.length() > 1) {
            baseViewHolder.setText(R.id.tv_goods_format, goodsFormat.substring(0, goodsFormat.length() - 1));
        }

        TextView status = baseViewHolder.getView(R.id.tv_goods_status);
        TextView changeStatus = baseViewHolder.getView(R.id.tv_change_status);
        if (Goods.STATUS_SOLD_OUT.equals(goods.getState()) && !Goods.APPLY_STATE_APPLYING.equals(goods.getApplyState())) {
            status.setText("状态：下架");
            changeStatus.setText("上架");
            changeStatus.setEnabled(true);
            changeStatus.setSelected(false);
            changeStatus.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        }

        if (Goods.STATUS_PUT_AWAY.equals(goods.getState())) {
            status.setText("状态：上架");
            changeStatus.setText("下架");
            changeStatus.setEnabled(true);
            changeStatus.setSelected(true);
            changeStatus.setTextColor(ContextCompat.getColor(mContext,R.color.red_f94745));
        }

        if (Goods.STATUS_SOLD_OUT.equals(goods.getState()) && Goods.APPLY_STATE_APPLYING.equals(goods.getApplyState())) {
            status.setText("状态：审核中");
            if (UserHelper.isAgent()){
                status.setEnabled(true);
                changeStatus.setText("上架");
            }
            if (UserHelper.isMerchant()) {
                changeStatus.setEnabled(false);
                changeStatus.setText("审核中");
            }
            changeStatus.setSelected(false);
            changeStatus.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        }

        changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemBtnClick(goods);
                }
            }
        });
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemBtnClick(Goods goods);
    }
}
