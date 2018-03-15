package com.yaya.merchant.activity.user;

import android.view.View;
import android.widget.FrameLayout;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseActivity;

import butterknife.BindView;

/**
 * 员工管理
 */

public class EmployeeManagerActivity extends BaseActivity {

    @BindView(R.id.fl_status)
    protected FrameLayout statusFl;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_merchat_manager;
    }

    @Override
    protected void initView() {
        super.initView();
        statusFl.setVisibility(View.GONE);
    }
}
