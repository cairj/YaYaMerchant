package com.yaya.merchant.widgets.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.goods.Goods;

import java.util.List;

/**
 * Created by admin on 2018/9/3.
 */

public class GoodsSaleRankAdapter extends BaseQuickAdapter<Goods> {
    public GoodsSaleRankAdapter(List<Goods> data) {
        super(R.layout.item_goods_sale_rank,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Goods goods) {
        baseViewHolder.setText(R.id.tv_goods_name,goods.getGoodsName())
                .setText(R.id.tv_sale_num,goods.getGoodsRealSale());
    }
}
