package com.yaya.merchant.widgets.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.order.OrderDetail;
import com.yaya.merchant.data.order.OrderDetailData;
import com.yaya.merchant.util.DpPxUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

/**
 * Created by admin on 2018/3/25.
 */

public class OrderListAdapter extends BaseQuickAdapter<OrderDetail> {

    private RecyclerView.ItemDecoration decoration;
    private int type;

    public OrderListAdapter(List<OrderDetail> data) {
        super(R.layout.item_order_list, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final OrderDetail orderData) {
        baseViewHolder.setText(R.id.tv_user_name, "平台用户：" + orderData.getMemberInfo())
                .setText(R.id.tv_date, orderData.getPayTime())
                .setText(R.id.tv_pay_status, orderData.getPayStatus())
                .setText(R.id.tv_total_money, "￥"+String.valueOf(totalPrice(orderData)));

        if (!TextUtils.isEmpty(orderData.getType())) {
            baseViewHolder.setText(R.id.tv_type, orderData.getType());
        }

        if (!TextUtils.isEmpty(orderData.getRefundReason())) {
            baseViewHolder.setText(R.id.tv_type, orderData.getRefundReason());
        }

        RecyclerView recyclerView = baseViewHolder.getView(R.id.rv_order_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        addItemDecoration(recyclerView);
        OrderDetailAdapter detailAdapter = new OrderDetailAdapter(orderData.getOrderdetail());
        recyclerView.setAdapter(detailAdapter);
        detailAdapter.setListener(new OrderDetailAdapter.OnClickListener() {
            @Override
            public void onParentClick() {
                if (listener!=null){
                    listener.onParentClick(orderData);
                }
            }
        });

        TextView btn = baseViewHolder.getView(R.id.tv_btn);
        switch (type) {
            case OrderDetail.TYPE_DELIVER_ORDER_LIST:
                btn.setText("发货");
                btn.setVisibility(View.VISIBLE);
                break;
            case OrderDetail.TYPE_REFUND_ORDER_LIST:
                btn.setText("审核");
                baseViewHolder.getView(R.id.tv_pay_status).setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
                break;
            default:
                btn.setVisibility(View.GONE);
                break;
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onBtnClick(orderData);
                }
            }
        });

        baseViewHolder.getView(R.id.ll_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onParentClick(orderData);
                }
            }
        });
    }

    protected void addItemDecoration(RecyclerView recyclerView) {
        if (decoration == null) {
            decoration = new HorizontalDividerItemDecoration.Builder(mContext)
                    .color(ContextCompat.getColor(mContext, R.color.white))
                    .size(DpPxUtil.dp2px(3))
                    .build();
        }
        recyclerView.removeItemDecoration(decoration);
        recyclerView.addItemDecoration(decoration);
    }

    //计算总价
    private double totalPrice(OrderDetail orderData) {
        double totalPrice = 0;
        for (OrderDetailData detailData : orderData.getOrderdetail()) {
            totalPrice += detailData.getOrderPrice() * detailData.getOrderNum();
        }
        return totalPrice;
    }

    public void setType(int type) {
        this.type = type;
    }


    private OnClickListener listener;

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener{
        void onParentClick(OrderDetail orderData);
        void onBtnClick(OrderDetail orderData);
    }

}
