package com.yaya.merchant.activity.order;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.OrderAction;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.order.OrderDetail;
import com.yaya.merchant.widgets.adapter.OrderListAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 蔡蓉婕 on 2018/9/14.
 */

public class RefundOrderListActivity extends BasePtrRecycleActivity<OrderDetail> {

    private String auditStatus;

    @BindView(R.id.tv_today)
    protected TextView todayTv;
    @BindView(R.id.tv_yesterday)
    protected TextView yesterdayTv;
    @BindView(R.id.tv_this_month)
    protected TextView thisMonthTv;
    @BindView(R.id.tv_custom)
    protected TextView customTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initView() {
        super.initView();
        customTv.setVisibility(View.GONE);
        todayTv.setText("客户申请");
        yesterdayTv.setText("财务审核");
        thisMonthTv.setText("已完成");
        todayTv.setSelected(true);
    }

    @Override
    protected void initData() {
        auditStatus = OrderDetail.REFUND_ORDER_STATUS_APPLYING;
        super.initData();
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        OrderListAdapter adapter = new OrderListAdapter(mDataList);
        adapter.setType(OrderDetail.TYPE_REFUND_ORDER_LIST);
        adapter.setListener(new OrderListAdapter.OnClickListener() {
            @Override
            public void onParentClick(OrderDetail orderData) {
                OrderDetailActivity.open(RefundOrderListActivity.this, orderData.getOrderSn());
            }

            @Override
            public void onBtnClick(OrderDetail orderDetail) {
                RefundOrderActivity.open(RefundOrderListActivity.this, orderDetail);
            }
        });
        return adapter;
    }

    @Override
    protected JsonResponse<BaseRowData<OrderDetail>> getData() throws Exception {
        return OrderAction.getRefundOrderList(auditStatus,String.valueOf(mCurrentPos),String.valueOf(pageSize));
    }

    @OnClick({R.id.tv_today, R.id.tv_yesterday, R.id.tv_this_month})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_today:
                todayTv.setSelected(true);
                yesterdayTv.setSelected(false);
                thisMonthTv.setSelected(false);
                auditStatus = OrderDetail.REFUND_ORDER_STATUS_APPLYING;
                break;
            case R.id.tv_yesterday:
                todayTv.setSelected(false);
                yesterdayTv.setSelected(true);
                thisMonthTv.setSelected(false);
                auditStatus = OrderDetail.REFUND_ORDER_STATUS_WAITING;
                break;
            case R.id.tv_this_month:
                todayTv.setSelected(false);
                yesterdayTv.setSelected(false);
                thisMonthTv.setSelected(true);
                auditStatus = OrderDetail.REFUND_ORDER_STATUS_SUCCESS;
                break;
        }
        refresh();
    }
}
