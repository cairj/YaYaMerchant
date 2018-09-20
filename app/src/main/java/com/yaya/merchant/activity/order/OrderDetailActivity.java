package com.yaya.merchant.activity.order;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.OrderAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.order.OrderDetail;
import com.yaya.merchant.data.order.OrderDetail;
import com.yaya.merchant.data.order.OrderDetailData;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.util.EventBusTags;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.widgets.adapter.OrderDetailAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/25.
 */

public class OrderDetailActivity extends BaseActivity {

    private String orderSn;
    private String type;
    private String orderId;

    public static void open(Context context, OrderDetail order, String type) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("order", order);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    private List<OrderDetailData> list = new ArrayList<>();
    private OrderDetailAdapter detailAdapter;
    protected OrderDetail orderDetail;

    @BindView(R.id.tv_status)
    protected TextView statusTv;
    @BindView(R.id.tv_buyer_info)
    protected TextView buyerInfoTv;
    @BindView(R.id.tv_user_name)
    protected TextView userNameTv;
    @BindView(R.id.tv_date)
    protected TextView dateTv;
    @BindView(R.id.tv_goods_price)
    protected TextView goodsPriceTv;
    @BindView(R.id.tv_deliver_price)
    protected TextView deliverPriceTv;
    @BindView(R.id.tv_total_price)
    protected TextView totalPriceTv;
    @BindView(R.id.tv_order_number)
    protected TextView orderNumberTv;
    @BindView(R.id.tv_order_time)
    protected TextView orderTimeTv;
    @BindView(R.id.tv_pay_type)
    protected TextView payTypeTv;
    @BindView(R.id.tv_delivery_type)
    protected TextView deliveryTypeTv;
    @BindView(R.id.rv_order_detail)
    protected RecyclerView orderDetailRv;
    @BindView(R.id.ll_bottom)
    protected LinearLayout bottomLL;
    @BindView(R.id.tv_submit)
    protected TextView submitTv;

    @BindView(R.id.iv_call)
    protected ImageView callIv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("订单详情");
        setStatusBarColor();
        OrderDetail order = (OrderDetail) getIntent().getSerializableExtra("order");
        orderSn = order.getOrderSn();
        orderId = order.getId();
        type = getIntent().getStringExtra("type");
        if (type == null){
            type = "";
        }
        switch (type) {
            case OrderDetail.TYPE_ORDER_LIST:
                bottomLL.setVisibility(View.GONE);
                break;
            case OrderDetail.TYPE_DELIVER_ORDER_LIST:
                bottomLL.setVisibility(View.VISIBLE);
                submitTv.setText("去发货");
                break;
            case OrderDetail.TYPE_REFUND_ORDER_LIST:
                bottomLL.setVisibility(View.VISIBLE);
                submitTv.setText("审核");
                break;
        }

        orderDetailRv.setLayoutManager(new LinearLayoutManager(this));
        orderDetailRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.white))
                .size(DpPxUtil.dp2px(3))
                .build());
        detailAdapter = new OrderDetailAdapter(list);
        orderDetailRv.setAdapter(detailAdapter);
    }

    protected void setStatusBarColor(){
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
    }

    @Override
    protected void initData() {
        super.initData();
        OrderAction.getOrderDetail(orderSn, new GsonCallback<OrderDetail>(OrderDetail.class) {
            @Override
            public void onSucceed(JsonResponse<OrderDetail> response) {
                orderDetail = response.getResultData();
                orderDetail.setId(orderId);
                fillOrderDetailData(orderDetail);
            }
        });
    }

    protected void fillOrderDetailData(OrderDetail orderDetail){
        statusTv.setText(orderDetail.getOrderStatus());
        buyerInfoTv.setText(orderDetail.getUserName() + "  " + orderDetail.getUserPhone() + "\n" + orderDetail.getUserAddress());
        dateTv.setText(orderDetail.getPayTime());
        goodsPriceTv.setText("￥" + orderDetail.getOrderPrice());
        deliverPriceTv.setText("￥" + orderDetail.getDeliverPrice());
        float totalPrice = orderDetail.getOrderPrice()+orderDetail.getDeliverPrice();
        totalPriceTv.setText("￥" + totalPrice);
        orderNumberTv.setText("订单编号：" + orderDetail.getOrderSn());
        orderTimeTv.setText("下单时间：" + orderDetail.getCreationTime());
        payTypeTv.setText("支付方式：" + orderDetail.getPaySource());
        deliveryTypeTv.setText("配送方式：" + orderDetail.getType());
        userNameTv.setText(orderDetail.getMemberInfo());
        list.clear();
        list.addAll(orderDetail.getOrderdetail());
        detailAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_submit)
    protected void OnClick() {
        switch (type) {
            case OrderDetail.TYPE_DELIVER_ORDER_LIST:
                DeliverOrderActivity.open(this, orderDetail);
                break;
            case OrderDetail.TYPE_REFUND_ORDER_LIST:
                RefundOrderActivity.open(this, orderDetail);
                break;
        }
    }

    @Subscriber(tag= EventBusTags.DELIVER_ORDER_SUCCESS)
    private void deliverOrderSuccess(String str){
        finish();
    }

}
