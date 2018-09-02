package com.yaya.merchant.activity.user;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.data.user.MerchantDetail;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.ChartUtils;

import butterknife.BindView;

/**
 * Created by admin on 2018/9/2.
 */

public class MerchantDetailActivity extends BaseActivity {

    private String shopId;

    @BindView(R.id.chart_line)
    LineChart dataChartLine;
    @BindView(R.id.tv_merchant_name)
    TextView merchantNameTv;
    @BindView(R.id.tv_merchant_master)
    TextView merchantMasterTv;
    @BindView(R.id.tv_merchant_phone)
    TextView merchantPhoneTv;
    @BindView(R.id.tv_merchant_address)
    TextView merchantAddressTv;
    @BindView(R.id.tv_merchant_data_goods)
    TextView merchantDataGoodsTv;
    @BindView(R.id.tv_merchant_data_order)
    TextView merchantDataOrderTv;
    @BindView(R.id.tv_merchant_data_money)
    TextView merchantDataMoneyTv;
    @BindView(R.id.tv_merchant_data_user)
    TextView merchantDataUserTv;

    public static void open(Context context,String shopId){
        Intent intent = new Intent(context,MerchantDetailActivity.class);
        intent.putExtra("shopId",shopId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_merchant_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        ChartUtils.initChart(dataChartLine);
    }

    @Override
    protected void initData() {
        super.initData();
        shopId = getIntent().getStringExtra("shopId");
        UserAction.getMerchantDetail(shopId, new GsonCallback<MerchantDetail>(MerchantDetail.class) {
            @Override
            public void onSucceed(JsonResponse<MerchantDetail> response) {
                MerchantDetail detail = response.getResultData();
                merchantNameTv.setText(detail.getShopName());
                merchantMasterTv.setText(detail.getUserName());
                merchantPhoneTv.setText(detail.getUserPhone());
                merchantAddressTv.setText(detail.getProvince()+detail.getCity()+detail.getDistrict()+detail.getShopAddress());
                merchantDataGoodsTv.setText(detail.getGoodsCount());
                merchantDataMoneyTv.setText(detail.getMoneyCount());
                merchantDataOrderTv.setText(detail.getOrderNum());
                merchantDataUserTv.setText(detail.getUserCount());
            }
        });
    }
}
