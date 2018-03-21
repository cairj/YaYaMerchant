package com.yaya.merchant.activity.user;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @BindView(R.id.tv_order_id)
    protected TextView orderIdTv;
    @BindView(R.id.tv_pay_time)
    protected TextView payTimeTv;
    @BindView(R.id.tv_total_money)
    protected TextView totalMoneyTv;
    @BindView(R.id.tv_goods_name)
    protected TextView goodsNameTv;
    @BindView(R.id.rv_images)
    protected RecyclerView imagesRv;

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
        totalMoneyTv.setText("商品总价：￥" + verificationInfo.getOrderPrice());
        payTimeTv.setText("付款时间：" + verificationInfo.getPayTime());
        goodsNameTv.setText(verificationInfo.getProductName().replaceAll(",", "\n"));

        imagesRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<String> imgList = new ArrayList<>();
        Collections.addAll(imgList, verificationInfo.getImg().split(","));
        imagesRv.setAdapter(new VerificationResultAdapter(imgList));
        imagesRv.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.gray_F6F7F9))
                .size(DpPxUtil.dp2px(15))
                .build());
    }

    @OnClick(R.id.tv_submit_verfication)
    protected void onClick() {
        UserAction.verification(Urls.VERIFICATION_SET, verificationInfo.getVerificationSn(), new GsonCallback<VerificationInfo>(VerificationInfo.class) {
            @Override
            public void onSucceed(JsonResponse<VerificationInfo> response) {
                ToastUtil.toast("核销成功");
                finish();
            }
        });
    }

}
