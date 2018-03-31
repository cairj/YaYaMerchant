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
import com.yaya.merchant.net.callback.GsonCallback;

import butterknife.BindView;

/**
 * Created by admin on 2018/3/31.
 */

public class GatheringDetailActivity extends BaseActivity {

    private String id;
    private String url;

    @BindView(R.id.tv_sum_price)
    protected TextView sumPriceTv;
    @BindView(R.id.tv_sum_price_title)
    protected TextView sumPriceTitleTv;
    @BindView(R.id.rl_balance)
    protected RelativeLayout balanceRl;
    @BindView(R.id.tv_member_balance)
    protected TextView balanceTv;
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

    public static void open(Context context, String id,String url) {
        Intent intent = new Intent(context, GatheringDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_gathering_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        setActionBarTitle("收款详情");
        id = getIntent().getStringExtra("id");
        url = getIntent().getStringExtra("url");
        MainDataAction.getGatheringDetail(id, url, new GsonCallback<GatheringData>(GatheringData.class) {
            @Override
            public void onSucceed(JsonResponse<GatheringData> response) {
                GatheringData data = response.getData().getData();
                if (TextUtils.isEmpty(data.getBalancePrice())){
                    sumPriceTitleTv.setText("消费金额（元）");
                    balanceRl.setVisibility(View.INVISIBLE);
                }else {
                    sumPriceTitleTv.setText("会员消费（元）");
                    sumPriceTitleTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.me_ic_vip,0,0,0);
                    balanceTv.setText("￥"+data.getBalancePrice());
                    balanceRl.setVisibility(View.VISIBLE);
                }

                sumPriceTv.setText(data.getOrderSumprice());
                orderPriceTv.setText("￥"+data.getOrderPrice());
                merchantNameTv.setText(data.getStoreName());
                createTimeTv.setText(data.getCreationTime().replace("T"," "));
                payTimeTv.setText(data.getPayTime().replace("T"," "));
                payTypeTv.setText(data.getOrderType());
                orderNumberTv.setText(data.getOrderSn());
            }
        });
    }
}
