package com.yaya.merchant.activity.balance;

import android.content.Intent;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseTabLayoutActivity;
import com.yaya.merchant.fragment.balance.BalanceAccountFragment;

import butterknife.OnClick;

/**
 * Created by 蔡蓉婕 on 2018/3/9.
 */

public class BalanceAccountActivity extends BaseTabLayoutActivity {

    public static int TO_MERCHANT_REQUEST_CODE = 10000;

    private BalanceAccountFragment balanceAccountFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_balance_account;
    }

    @Override
    protected void initData() {
        balanceAccountFragment = new BalanceAccountFragment();
        fragmentList.add(balanceAccountFragment);

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }
        if (requestCode == TO_MERCHANT_REQUEST_CODE) {
            String selectMerchantId = data.getStringExtra(SearchMerchantActivity.RETURN_SELECT_MERCHANT_ID);
            balanceAccountFragment.setSelectedMerchantId(selectMerchantId);
        }
    }

    @OnClick(R.id.tv_search)
    protected void toSearch() {
        if (viewPager.getCurrentItem() == 0) {
            startActivityForResult(SearchMerchantActivity
                            .openSearchIntent(this, balanceAccountFragment.getSelectedMerchantId()),
                    TO_MERCHANT_REQUEST_CODE);
        }
    }

}
