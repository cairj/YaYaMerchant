package com.yaya.merchant.activity.account;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.account.GatheringData;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.net.callback.GsonCallback;

import butterknife.BindView;

/**
 * Created by admin on 2018/3/31.
 */

public class RefundDetailActivity extends BaseActivity {

    private String id;

    @BindView(R.id.tv_refund_money)
    protected TextView refundMoneyTv;
    @BindView(R.id.tv_refund_user)
    protected TextView refundUserTv;
    @BindView(R.id.tv_refund_time)
    protected TextView refundTimeTv;
    @BindView(R.id.tv_order_refund_price)
    protected TextView orderRefundPriceTv;
    @BindView(R.id.tv_order_price)
    protected TextView orderPriceTv;
    @BindView(R.id.tv_create_time)
    protected TextView createTimeTv;
    @BindView(R.id.tv_pay_time)
    protected TextView payTimeTv;
    @BindView(R.id.tv_pay_type)
    protected TextView payTypeTv;
    @BindView(R.id.tv_merchant_name)
    protected TextView merchantNameTv;
    @BindView(R.id.tv_order_number)
    protected TextView orderNumberTv;
    @BindView(R.id.tv_pay_user)
    protected TextView payUserTv;
    @BindView(R.id.tv_merchant_number)
    protected TextView merchantNumberTv;
    @BindView(R.id.tv_remark)
    protected TextView remarkTv;
    @BindView(R.id.tv_refund_status)
    protected TextView refundStatusTv;

    public static void open(Context context, String id) {
        Intent intent = new Intent(context, RefundDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_refund_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        setActionBarTitle("退款详情");
        id = getIntent().getStringExtra("id");
        MainDataAction.getGatheringDetail(id, Urls.GET_HOUSTON_DETAIL, new GsonCallback<GatheringData>(GatheringData.class) {
            @Override
            public void onSucceed(JsonResponse<GatheringData> response) {
                GatheringData data = response.getData().getData();

                merchantNameTv.setText(data.getStoreName());
                createTimeTv.setText(data.getCreationTime().replace("T", " "));
                payTimeTv.setText(data.getPayTime().replace("T", " "));
                payTypeTv.setText(data.getOrderType());
                orderNumberTv.setText(data.getOrderSn());
                refundMoneyTv.setText(data.getRefundAmount());
                refundUserTv.setText(data.getName());
                refundTimeTv.setText(data.getRefundTime().replace("T", " "));
                orderRefundPriceTv.setText(data.getRefundAmount());
                orderPriceTv.setText(data.getOrderPrice());
                payUserTv.setText(data.getPayName());
                merchantNumberTv.setText(data.getTradeNo());
                refundStatusTv.setText(data.getRefundState());
                if (data.getRefundState().contains("成功")) {
                    refundStatusTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_refund_complete, 0, 0, 0);
                }
            }
        });
    }

}
