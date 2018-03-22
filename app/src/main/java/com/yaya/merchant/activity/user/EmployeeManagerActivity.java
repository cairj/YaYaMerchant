package com.yaya.merchant.activity.user;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.activity.search.EmployeeSearchActivity;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.user.EmployeeData;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.widgets.adapter.EmployeeManagerAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 员工管理
 */

public class EmployeeManagerActivity extends BasePtrRecycleActivity<EmployeeData> {

    private String search;

    @BindView(R.id.fl_status)
    protected FrameLayout statusFl;
    @BindView(R.id.tv_status)
    protected TextView statusTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_merchat_manager;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new EmployeeManagerAdapter(mDataList);
    }

    @Override
    protected JsonResponse<BaseRowData<EmployeeData>> getData() throws Exception {
        return UserAction.getEmployeeList(search, String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void initView() {
        super.initView();
        statusFl.setVisibility(View.GONE);
        setActionBarTitle("员工");
        statusFl.setVisibility(View.GONE);
        recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_F6F7F9));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ptrFrame.getLayoutParams();
        lp.topMargin = DpPxUtil.dp2px(10);
        ptrFrame.setLayoutParams(lp);

        statusTv.setText("");
        statusTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    @OnClick({R.id.fl_search})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_search:
                openActivity(EmployeeSearchActivity.class);
                break;
        }
    }

}
