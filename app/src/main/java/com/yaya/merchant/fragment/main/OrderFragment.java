package com.yaya.merchant.fragment.main;

import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainAction;
import com.yaya.merchant.activity.order.OrderListActivity;
import com.yaya.merchant.activity.user.VerificationActivity;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.data.main.OrderData;
import com.yaya.merchant.net.callback.GsonCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/7.
 */

public class OrderFragment extends BaseFragment {

    @BindView(R.id.order_tv_amount)
    protected TextView amountTv;
    @BindView(R.id.tv_already_order)
    protected TextView alreadyOrderTv;
    @BindView(R.id.tv_waiting_receive)
    protected TextView waitingReceiveTv;
    @BindView(R.id.tv_return)
    protected TextView returnTv;
    @BindView(R.id.tv_receive_count)
    protected TextView receiveCountTv;
    @BindView(R.id.tv_return_count)
    protected TextView returnCountTv;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initData() {
        super.initData();
        MainAction.getOrderData(new GsonCallback<OrderData>(OrderData.class) {
            @Override
            public void onSucceed(JsonResponse<OrderData> response) {
                OrderData data = response.getData().getData();
                amountTv.setText(data.getPricetotal());
                alreadyOrderTv.setText(data.getOrderprice());
                waitingReceiveTv.setText(data.getWaitReceivePrice());
                returnTv.setText(data.getRefundPrice());
                if (Integer.parseInt(data.getWaitReceiveCount())>0) {
                    receiveCountTv.setText(data.getWaitReceiveCount());
                    receiveCountTv.setVisibility(View.VISIBLE);
                }
                if (Integer.parseInt(data.getRefundCount())>0) {
                    receiveCountTv.setText(data.getRefundCount());
                    receiveCountTv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.ll_order_list,R.id.tv_receive,R.id.tv_return_review,R.id.tv_verification})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.ll_order_list:
                OrderListActivity.open(getActivity(), com.yaya.merchant.data.order.OrderData.TYPE_ORDER_LIST);
                break;
            case R.id.tv_receive:
                OrderListActivity.open(getActivity(), com.yaya.merchant.data.order.OrderData.TYPE_DELIVER_ORDER_LIST);
                break;
            case R.id.tv_return_review:
                OrderListActivity.open(getActivity(), com.yaya.merchant.data.order.OrderData.TYPE_REFUND_ORDER_LIST);
                break;
            case R.id.tv_verification:
                new IntentIntegrator(getActivity())
                        .setOrientationLocked(false)
                        .setPrompt("将提货二维码放入框内即可自动扫描")
                        .setCaptureActivity(VerificationActivity.class) // 设置自定义的activity是VerificationActivity
                        .initiateScan(); // 初始化扫描
                break;
        }
    }

}
