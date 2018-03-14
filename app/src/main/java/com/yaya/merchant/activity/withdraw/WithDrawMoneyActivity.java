package com.yaya.merchant.activity.withdraw;

import android.widget.EditText;
import android.widget.TextView;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseActivity;

import butterknife.BindView;

/**
 * Created by admin on 2018/3/15.
 */

public class WithDrawMoneyActivity extends BaseActivity {

    @BindView(R.id.tv_bank_card)
    protected TextView bankCardTv;
    @BindView(R.id.ed_money_count)
    protected EditText moneyCountEdit;
    @BindView(R.id.tv_total_money)
    protected TextView totalMoneyTv;
    @BindView(R.id.tv_member_balance)
    protected TextView memberBalanceTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_withdraw_money;
    }

    @Override
    protected void initData() {

    }
}
