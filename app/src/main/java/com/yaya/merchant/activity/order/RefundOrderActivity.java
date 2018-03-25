package com.yaya.merchant.activity.order;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.OrderAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.order.OrderDetail;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/25.
 */

public class RefundOrderActivity extends BaseActivity {

    private OrderDetail orderDetail;

    public static void open(Context context, OrderDetail orderDetail) {
        Intent intent = new Intent(context, RefundOrderActivity.class);
        intent.putExtra("orderDetail", orderDetail);
        context.startActivity(intent);
    }

    @BindView(R.id.tv_refund_reason)
    protected TextView refundReasonTv;
    @BindView(R.id.cb_allow_refund)
    protected CheckBox allowRefundCb;
    @BindView(R.id.cb_disallow_refund)
    protected CheckBox disallowRefundCb;
    @BindView(R.id.ll_disallow_reason)
    protected LinearLayout disallowReasonLL;
    @BindView(R.id.edit_disallow_reason)
    protected EditText disallowReasonEdit;

    private boolean isAllowRefund = true;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_refund_order;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        orderDetail = (OrderDetail) getIntent().getSerializableExtra("orderDetail");
        refundReasonTv.setText(orderDetail.getRefundReason());
        setAllowCheckBox();
    }

    private void setAllowCheckBox() {
        allowRefundCb.setChecked(isAllowRefund);
        disallowRefundCb.setChecked(!isAllowRefund);
    }

    @OnClick({R.id.cb_allow_refund, R.id.cb_disallow_refund, R.id.tv_submit})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_allow_refund:
                isAllowRefund = true;
                disallowReasonLL.setVisibility(View.GONE);
                setAllowCheckBox();
                break;
            case R.id.cb_disallow_refund:
                isAllowRefund = false;
                disallowReasonLL.setVisibility(View.VISIBLE);
                setAllowCheckBox();
                break;
            case R.id.tv_submit:
                if (isAllowRefund) {
                    OrderAction.allowRefund(orderDetail.getOrderSn(), submitCallback);
                } else {
                    OrderAction.disallowRefund(orderDetail.getOrderSn(), disallowReasonEdit.getText().toString().trim(),
                            orderDetail.getMemberId(), submitCallback);
                }
                break;
        }
    }

    GsonCallback<String> submitCallback = new GsonCallback<String>(String.class) {
        @Override
        public void onSucceed(JsonResponse<String> response) {
            ToastUtil.toast(response.getData().getData());
        }
    };

}
