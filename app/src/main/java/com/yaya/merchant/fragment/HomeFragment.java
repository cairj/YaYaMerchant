package com.yaya.merchant.fragment;

import android.widget.TextView;

import com.yaya.merchant.R;
import com.yaya.merchant.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by admin on 2018/3/6.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.home_tv_amount)
    protected TextView amountTv;
    @BindView(R.id.tv_today_transaction_number)
    protected TextView todayTransactionNumberTv;
    @BindView(R.id.tv_today_visitor_number)
    protected TextView todayVisitorNumberTv;
    @BindView(R.id.tv_today_new_member_number)
    protected TextView todayNewMemberNumberTv;
    @BindView(R.id.tv_total_transaction_number)
    protected TextView totalTransactionNumberTv;
    @BindView(R.id.tv_total_member_number)
    protected TextView totalMemberNumberTv;
    @BindView(R.id.tv_total_order_number)
    protected TextView totalOrderNumberTv;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {

    }
}
