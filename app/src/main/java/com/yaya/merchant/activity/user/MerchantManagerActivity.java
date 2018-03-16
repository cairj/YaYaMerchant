package com.yaya.merchant.activity.user;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.user.MerchantData;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.widgets.adapter.MerchantManagerAdapter;

import butterknife.BindView;

/**
 * 门店管理
 */

public class MerchantManagerActivity extends BasePtrRecycleActivity<MerchantData> {

    private String status;
    private String search;

    @BindView(R.id.fl_status)
    protected FrameLayout statusFl;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_merchat_manager;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MerchantManagerAdapter(mDataList);
    }

    @Override
    protected JsonResponse<BaseRowData<MerchantData>> getData() throws Exception {
        return UserAction.getMerchantList(status, search, String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("门店");
        statusFl.setVisibility(View.GONE);
        recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_F6F7F9));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ptrFrame.getLayoutParams();
        lp.topMargin = DpPxUtil.dp2px(10);
        ptrFrame.setLayoutParams(lp);
    }
}
