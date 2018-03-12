package com.yaya.merchant.activity.account;

import android.content.Intent;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseTabLayoutActivity;
import com.yaya.merchant.fragment.account.MemberBillFragment;
import com.yaya.merchant.fragment.account.MerchantBillFragment;

import butterknife.OnClick;

/**
 * 入账列表
 */

public class EnterBillActivity extends BaseTabLayoutActivity {

    public static int TO_MERCHANT_REQUEST_CODE = 10000;

    private MerchantBillFragment merchantBillFragment;
    private MemberBillFragment memberBillFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_balance_account;
    }

    @Override
    protected void initData() {
        merchantBillFragment = new MerchantBillFragment();
        fragmentList.add(merchantBillFragment);

        memberBillFragment = new MemberBillFragment();
        fragmentList.add(memberBillFragment);

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
            merchantBillFragment.setSelectedMerchantId(selectMerchantId);
        }
    }

    @OnClick(R.id.tv_search)
    protected void toSearch() {
        if (viewPager.getCurrentItem() == 0) {
            startActivityForResult(SearchMerchantActivity
                            .openSearchIntent(this, merchantBillFragment.getSelectedMerchantId()),
                    TO_MERCHANT_REQUEST_CODE);
        }

        if (viewPager.getCurrentItem() == 1) {
            startActivityForResult(SearchMerchantActivity
                            .openSearchIntent(this, memberBillFragment.getSelectedMerchantId()),
                    TO_MERCHANT_REQUEST_CODE);
        }

    }

}
