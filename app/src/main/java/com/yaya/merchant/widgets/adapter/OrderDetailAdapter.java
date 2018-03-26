package com.yaya.merchant.widgets.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.order.OrderDetailData;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import java.util.List;

/**
 * Created by admin on 2018/3/25.
 */

public class OrderDetailAdapter extends BaseQuickAdapter<OrderDetailData> {

    public OrderDetailAdapter(List<OrderDetailData> data) {
        super(R.layout.item_order_detail,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, OrderDetailData orderDetailData) {
        baseViewHolder.setText(R.id.tv_name,orderDetailData.getProductName())
                .setText(R.id.tv_attribute,orderDetailData.getSpecName())
                .setText(R.id.tv_remark,orderDetailData.getOrderRemarks())
                .setText(R.id.tv_price,"￥"+String.valueOf(orderDetailData.getOrderPrice()))
                .setText(R.id.tv_count,String.valueOf("X"+orderDetailData.getOrderNum()));

        GlideLoaderHelper.loadImg(orderDetailData.getImg(), (ImageView) baseViewHolder.getView(R.id.iv_pic));

        baseViewHolder.getView(R.id.ll_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onParentClick();
                }
            }
        });
    }

    private OnClickListener listener;

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener{
        void onParentClick();
    }
}
