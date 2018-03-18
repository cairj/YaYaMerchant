package com.yaya.merchant.activity.user;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.activity.search.MemberSearchActivity;
import com.yaya.merchant.activity.search.MerchantSearchActivity;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.user.MerchantData;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.widgets.adapter.MerchantManagerAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 门店管理
 */

public class MerchantManagerActivity extends BasePtrRecycleActivity<MerchantData> {

    public static final int MERCHANT_MANAGER = 1;
    public static final int MERCHANT_QR_CODE = 2;//二维码

    private String status;
    private String search;
    private int type;

    public static void open(Context context, int type) {
        Intent intent = new Intent(context, MerchantManagerActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @BindView(R.id.fl_status)
    protected FrameLayout statusFl;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_merchat_manager;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        MerchantManagerAdapter adapter = new MerchantManagerAdapter(mDataList);
        adapter.setListener(new MerchantManagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(MerchantData merchantData) {
                if (type == MERCHANT_QR_CODE) {
                    MerchantQRCodeActivity.open(MerchantManagerActivity.this, merchantData.getStoreId(),
                            merchantData.getName());
                }
            }
        });
        return adapter;
    }

    @Override
    protected JsonResponse<BaseRowData<MerchantData>> getData() throws Exception {
        return UserAction.getMerchantList(status, search, String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", MERCHANT_MANAGER);
        super.initView();
        setActionBarTitle("门店");
        statusFl.setVisibility(View.GONE);
        recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_F6F7F9));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ptrFrame.getLayoutParams();
        lp.topMargin = DpPxUtil.dp2px(10);
        ptrFrame.setLayoutParams(lp);
    }

    @OnClick({R.id.fl_search})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.fl_search:
                openActivity(MerchantSearchActivity.class);
                break;
        }
    }

}
