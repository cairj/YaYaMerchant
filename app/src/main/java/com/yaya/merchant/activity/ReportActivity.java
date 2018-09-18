package com.yaya.merchant.activity;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseTabLayoutActivity;
import com.yaya.merchant.fragment.report.OrderReportFragment;
import com.yaya.merchant.fragment.report.SaleReportFragment;

/**
 * Created by 蔡蓉婕 on 2018/9/18.
 */

public class ReportActivity extends BaseTabLayoutActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_report;
    }

    @Override
    protected void initData() {
        super.initData();

        tabTitleList.add("订单");
        fragmentList.add(new OrderReportFragment());

        tabTitleList.add("销售量");
        fragmentList.add(new SaleReportFragment());

        adapter.notifyDataSetChanged();

    }
}
