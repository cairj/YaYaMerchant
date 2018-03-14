package com.yaya.merchant.activity.account;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseTabLayoutActivity;
import com.yaya.merchant.fragment.account.MemberManagerFragment;

/**
 * Created by admin on 2018/3/14.
 */

public class MemberManagerActivity extends BaseTabLayoutActivity {

    private MemberManagerFragment memberManagerFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_member_manager;
    }

    @Override
    protected void initData() {
        super.initData();

        memberManagerFragment = new MemberManagerFragment();
        fragmentList.add(memberManagerFragment);

        adapter.notifyDataSetChanged();
    }
}
