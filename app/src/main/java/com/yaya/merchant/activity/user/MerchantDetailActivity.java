package com.yaya.merchant.activity.user;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.data.user.MerchantDetail;
import com.yaya.merchant.data.user.MerchantReportForms;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.ChartUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

        getReportForms(Urls.MERCHANT_REPORT_FORMS_GOODS);
    }

    private void getReportForms(String url){
        UserAction.getMerchantReportForms(url, shopId, new GsonCallback<MerchantReportForms>(MerchantReportForms.class) {
            @Override
            public void onSucceed(JsonResponse<MerchantReportForms> response) {
                List<Entry> entries = new ArrayList<>();
                MerchantReportForms data = response.getResultData();
                for (int i = 0; i < data.getNums().size(); i++) {
                    /**
                     * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
                     * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
                     */
                    Entry entry = new Entry(i, data.getNums().get(i));
                    entries.add(entry);
                }
                ChartUtils.setChartData(dataChartLine,entries);
                ChartUtils.setXAxis(dataChartLine,data.getDay());
            }
        });
    }

    @OnClick({R.id.fl_merchant_data_goods,R.id.fl_merchant_data_money,R.id.fl_merchant_data_order,R.id.fl_merchant_data_user})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.fl_merchant_data_goods:
                getReportForms(Urls.MERCHANT_REPORT_FORMS_GOODS);
                break;
            case R.id.fl_merchant_data_money:
                getReportForms(Urls.MERCHANT_REPORT_FORMS_MONEY);
                break;
            case R.id.fl_merchant_data_order:
                getReportForms(Urls.MERCHANT_REPORT_FORMS_ORDER);
                break;
            case R.id.fl_merchant_data_user:
                getReportForms(Urls.MERCHANT_REPORT_FORMS_USER);
                break;
        }
    }
}
