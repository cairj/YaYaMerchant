package com.yaya.merchant.activity.user;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.user.VerificationInfo;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.widgets.adapter.VerificationOrderAdapter;
import com.yaya.merchant.widgets.adapter.VerificationResultAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/20.
 */

public class VerificationResultActivity extends BaseActivity {

    private VerificationInfo verificationInfo;

    @BindView(R.id.tv_order_number)
    protected TextView orderIdTv;
    @BindView(R.id.tv_pay_time)
    protected TextView payTimeTv;
    @BindView(R.id.tv_goods_price)
    protected TextView totalMoneyTv;
    @BindView(R.id.tv_goods_name)
    protected TextView goodsNameTv;
    @BindView(R.id.tv_goods_count)
    protected TextView goodsCountTv;

    @BindView(R.id.tv_submit_verfication)
    protected TextView submitTv;
    @BindView(R.id.tv_status)
    protected TextView statusTv;

    @BindView(R.id.rv_order_list)
    protected RecyclerView ordersRv;

    private VerificationOrderAdapter orderAdapter;

    public static void open(Context context, VerificationInfo verificationInfo) {
        Intent intent = new Intent(context, VerificationResultActivity.class);
        intent.putExtra("verificationInfo", verificationInfo);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_verification_result;
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("核销");
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);

        verificationInfo = (VerificationInfo) getIntent().getSerializableExtra("verificationInfo");
        orderIdTv.setText("订单号：" + verificationInfo.getOrderSn());
        payTimeTv.setText("付款时间：" + verificationInfo.getPayTime());

        if (verificationInfo.getOrderdetail().size() > 1) {
            totalMoneyTv.setText("订单合计：￥" + verificationInfo.getTotalPrice());
            goodsNameTv.setVisibility(View.GONE);
            goodsCountTv.setVisibility(View.GONE);

            ordersRv.setLayoutManager(new LinearLayoutManager(this));
            orderAdapter = new VerificationOrderAdapter(verificationInfo.getOrderdetail());
            ordersRv.setAdapter(orderAdapter);
        }

        if (verificationInfo.getOrderdetail().size() == 1) {
            totalMoneyTv.setText("商品单价：￥" + verificationInfo.getTotalPrice());
            goodsNameTv.setText("商品名称：" + verificationInfo.getOrderdetail().get(0).getProductName());
            goodsCountTv.setText("商品数量：" + verificationInfo.getOrderdetail().get(0).getOrderNum());
            ordersRv.setVisibility(View.GONE);
        }

        if (verificationInfo.getStatus() == 1) {
            submitTv.setVisibility(View.VISIBLE);
            statusTv.setText("还未核销");
        } else {
            submitTv.setVisibility(View.GONE);
            statusTv.setText("此码已核销完成");
        }
    }

    @OnClick(R.id.tv_submit_verfication)
    protected void onClick() {
        UserAction.verification(verificationInfo.getOrder_id(), new GsonCallback<String>(String.class) {
            @Override
            public void onSucceed(JsonResponse<String> response) {
                submitTv.setVisibility(View.GONE);
                statusTv.setText("此码已核销完成");
            }
        });
    }

}
