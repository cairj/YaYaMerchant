package com.yaya.merchant.activity.account;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.account.GatheringData;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by admin on 2018/3/31.
 */

public class GatheringDetailActivity extends BaseActivity {

    private String id;
    private String type;

    @BindView(R.id.iv_avatar)
    protected ImageView avatarIv;
    @BindView(R.id.tv_name)
    protected TextView nameTv;

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
    @BindView(R.id.tv_merchant_real_price)
    protected TextView merchantRealPriceTv;
    @BindView(R.id.tv_merchant_number)
    protected TextView merchantNumberTv;
    @BindView(R.id.tv_order_remark)
    protected TextView orderRemarkTv;

    public static void open(Context context, String id,String type) {
        Intent intent = new Intent(context, GatheringDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
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
        type = getIntent().getStringExtra("type");
        MainDataAction.getGatheringDetail(id, type, new GsonCallback<GatheringData>(GatheringData.class) {
            @Override
            public void onSucceed(JsonResponse<GatheringData> response) {
                GatheringData data = response.getResultData();
                GlideLoaderHelper.loadImg(data.getHeadImg(),avatarIv);
                nameTv.setText(data.getName());

                sumPriceTitleTv.setCompoundDrawablesWithIntrinsicBounds("1".equals(data.getPayType())?R.mipmap.ic_weixin:R.mipmap.ic_zhifubao,0,0,0);
                sumPriceTv.setText(data.getOrderSumprice());
                merchantRealPriceTv.setText("￥"+data.getOrderSumprice());
                orderPriceTv.setText(data.getOrderPrice());
                merchantNameTv.setText(data.getStoreName());
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                createTimeTv.setText(sp.format(new Date(Integer.valueOf(data.getCreationTime()))));
                payTimeTv.setText(sp.format(new Date(Integer.valueOf(data.getPayTime()))));
                payTypeTv.setText(data.getOrderType());
                orderNumberTv.setText(data.getOrderSn());
                merchantNumberTv.setText(data.getTradeNo());
                orderRemarkTv.setText(data.getRemark());
            }
        });
    }
}
