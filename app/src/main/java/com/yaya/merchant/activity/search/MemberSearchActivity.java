package com.yaya.merchant.activity.search;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.data.account.Member;
import com.yaya.merchant.widgets.adapter.MemberManagerAdapter;

import butterknife.OnClick;

/**
 * Created by admin on 2018/3/17.
 */

public class MemberSearchActivity extends BaseSearchActivity<Member> {
    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MemberManagerAdapter(mDataList);
    }

    @Override
    protected JsonResponse<BaseRowData<Member>> getData() throws Exception {
        return MainDataAction.getMemberList(searchKey, "", String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void initView() {
        super.initView();
        inputSearchEt.setHint("搜索会员");
    }

}
