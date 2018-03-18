package com.yaya.merchant.activity.search;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.data.account.Member;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.data.user.MerchantData;
import com.yaya.merchant.widgets.adapter.MemberManagerAdapter;
import com.yaya.merchant.widgets.adapter.MerchantManagerAdapter;

/**
 * Created by admin on 2018/3/17.
 */

public class MerchantSearchActivity extends BaseSearchActivity<MerchantData> {
    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MerchantManagerAdapter(mDataList);
    }

    @Override
    protected JsonResponse<BaseRowData<MerchantData>> getData() throws Exception {
        return UserAction.getMerchantList("", searchKey,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void initView() {
        super.initView();
        inputSearchEt.setHint("搜索门店");
    }

}
