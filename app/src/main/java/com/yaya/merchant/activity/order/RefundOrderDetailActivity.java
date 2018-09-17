package com.yaya.merchant.activity.order;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.OrderAction;
import com.yaya.merchant.data.order.OrderDetail;
import com.yaya.merchant.data.order.RefundOrderDetail;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/9/16.
 */

public class RefundOrderDetailActivity extends OrderDetailActivity {

    protected RefundOrderDetail refundOrderDetail;

    @BindView(R.id.ll_service)
    protected LinearLayout serviceLL;
    @BindView(R.id.tv_service_opinion)
    protected TextView serviceOpinionTv;
    @BindView(R.id.tv_service_remark)
    protected TextView serviceRemarkTv;

    @BindView(R.id.ll_treasure)
    protected LinearLayout treasureLL;
    @BindView(R.id.tv_treasure_opinion)
    protected TextView treasureOpinionTv;
    @BindView(R.id.tv_treasure_status)
    protected TextView treasureStatusTv;
    @BindView(R.id.tv_treasure_remark)
    protected TextView treasureRemarkTv;

    public static void open(Context context, OrderDetail order, String type) {
        Intent intent = new Intent(context, RefundOrderDetailActivity.class);
        intent.putExtra("order", order);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_refund_order_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("订单详情");
        orderDetail = (OrderDetail) getIntent().getSerializableExtra("order");
        statusTv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setStatusBarColor() {
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData(){
        OrderAction.getRefundOrderDetail(orderDetail.getId(), orderDetail.getGoodsId(), new GsonCallback<RefundOrderDetail>(RefundOrderDetail.class) {
            @Override
            public void onSucceed(JsonResponse<RefundOrderDetail> response) {
                refundOrderDetail = response.getResultData();
                userNameTv.setText(refundOrderDetail.getOrderGoods().getShopName());
                fillOrderDetailData(orderDetail);
                fillOrderInfo();
                fillOrderStatus();
                fillOrderServiceData();
                fillOrderTreasureData();
            }
        });
    }

    private void fillOrderInfo() {
        orderNumberTv.setText("订单编号：" + refundOrderDetail.getOrderInfo().getOrderNumber());
        orderTimeTv.setText("下单时间：" + refundOrderDetail.getOrderInfo().getCreateTime());
        payTypeTv.setText("支付方式：" + refundOrderDetail.getOrderInfo().getPaymentType());
        deliveryTypeTv.setText("配送方式：" + refundOrderDetail.getOrderInfo().getShippingType());
    }

    private void fillOrderStatus() {
        bottomLL.setVisibility(View.GONE);
        switch (orderDetail.getAuditStatus()) {
            case OrderDetail.REFUND_ORDER_STATUS_APPLYING:
                bottomLL.setVisibility(View.VISIBLE);
                submitTv.setText("通过");
                statusTv.setText("客户：申请通过");
                break;
            case OrderDetail.REFUND_ORDER_STATUS_WAITING:
                statusTv.setText("商家：已同意退款");
                break;
            case OrderDetail.REFUND_ORDER_STATUS_SUCCESS:
                statusTv.setText("财务：同意退款");
                break;
        }
    }

    private void fillOrderServiceData() {
        if (refundOrderDetail.getServiceIdel() != null
                && !TextUtils.isEmpty(refundOrderDetail.getServiceIdel().getOpinion())
                && !TextUtils.isEmpty(refundOrderDetail.getServiceIdel().getRemark())) {
            serviceLL.setVisibility(View.VISIBLE);
            serviceOpinionTv.setText("处理意见:" + refundOrderDetail.getServiceIdel().getOpinion());
            serviceRemarkTv.setText("其他备注:" + refundOrderDetail.getServiceIdel().getRemark());
        } else {
            serviceLL.setVisibility(View.GONE);
        }
    }

    private void fillOrderTreasureData() {
        if (refundOrderDetail.getTreasure() != null
                && !TextUtils.isEmpty(refundOrderDetail.getTreasure().getOpinion())
                && !TextUtils.isEmpty(refundOrderDetail.getTreasure().getRemark())
                && !TextUtils.isEmpty(refundOrderDetail.getTreasure().getStatus())) {
            treasureLL.setVisibility(View.VISIBLE);
            treasureOpinionTv.setText("处理意见:" + refundOrderDetail.getTreasure().getOpinion());
            treasureRemarkTv.setText("其他备注:" + refundOrderDetail.getTreasure().getRemark());
            treasureStatusTv.setText("支付进度:" + refundOrderDetail.getTreasure().getStatus());
        } else {
            treasureLL.setVisibility(View.GONE);
        }
    }

    //同意或拒绝退款
    @OnClick({R.id.tv_cancel,R.id.tv_submit})
    protected void onOrderDispose(final View view){
        String url = "";
        switch (view.getId()){
            case R.id.tv_cancel:
                url = Urls.DISAGREE_ORDER_REFUND;
                break;
            case R.id.tv_submit:
                url = Urls.AGREE_ORDER_REFUND;
                break;
        }
        OrderAction.disposeRefund(url, orderDetail.getId(), orderDetail.getGoodsId(), new GsonCallback<String>(String.class) {
            @Override
            public void onSucceed(JsonResponse<String> response) {
                ToastUtil.toast("处理成功");
                switch (view.getId()){
                    case R.id.tv_cancel:
                        orderDetail.setAuditStatus(OrderDetail.REFUND_ORDER_STATUS_FAIL);
                        break;
                    case R.id.tv_submit:
                        orderDetail.setAuditStatus(OrderDetail.REFUND_ORDER_STATUS_SUCCESS);
                        break;
                }
                getData();
            }
        });
    }
}
