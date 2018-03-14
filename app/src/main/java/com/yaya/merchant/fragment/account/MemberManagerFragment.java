package com.yaya.merchant.fragment.account;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.base.fragment.BasePtrRecycleFragment;
import com.yaya.merchant.data.account.Member;
import com.yaya.merchant.widgets.adapter.MemberManagerAdapter;

/**
 * Created by admin on 2018/3/14.
 */

public class MemberManagerFragment extends BasePtrRecycleFragment<Member> {

    private String memberState;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_member_manager;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MemberManagerAdapter(mDataList);
    }

    @Override
    protected JsonResponse<BaseRowData<Member>> getData() throws Exception {
        return MainDataAction.getMemberList("", memberState, String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    public String getTitle() {
        return "管理";
    }
}
