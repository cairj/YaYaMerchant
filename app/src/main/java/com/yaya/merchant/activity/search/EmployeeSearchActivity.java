package com.yaya.merchant.activity.search;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.data.user.EmployeeData;
import com.yaya.merchant.data.user.MerchantData;
import com.yaya.merchant.widgets.adapter.EmployeeManagerAdapter;
import com.yaya.merchant.widgets.adapter.MerchantManagerAdapter;

/**
 * Created by admin on 2018/3/17.
 */

public class EmployeeSearchActivity extends BaseSearchActivity<EmployeeData> {
    @Override
    protected BaseQuickAdapter getAdapter() {
        return new EmployeeManagerAdapter(mDataList);
    }

    @Override
    protected JsonResponse<BaseRowData<EmployeeData>> getData() throws Exception {
        return UserAction.getEmployeeList(searchKey, String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void initView() {
        super.initView();
        inputSearchEt.setHint("搜索员工");
    }
}
