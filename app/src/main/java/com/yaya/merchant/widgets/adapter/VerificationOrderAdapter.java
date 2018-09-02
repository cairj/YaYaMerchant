package com.yaya.merchant.widgets.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.order.OrderDetailData;

import java.util.List;

/**
 * Created by admin on 2018/9/3.
 */

public class VerificationOrderAdapter extends BaseQuickAdapter<OrderDetailData> {
    public VerificationOrderAdapter(List<OrderDetailData> data) {
        super(R.layout.item_verification_order,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, OrderDetailData orderDetailData) {
        baseViewHolder.setText(R.id.tv_goods_name,"商品名称："+orderDetailData.getProductName())
                .setText(R.id.tv_goods_price,"商品单价：￥" +orderDetailData.getOrderPrice())
                .setText(R.id.tv_goods_count,"商品数量："+orderDetailData.getOrderNum());
    }
}
