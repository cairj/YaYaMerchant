package com.yaya.merchant.fragment.account;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.action.BalanceAccountAction;
import com.yaya.merchant.base.fragment.BasePtrRecycleFragment;
import com.yaya.merchant.data.account.BillData;

/**
 * Created by 蔡蓉婕 on 2018/3/12.
 */

public class MemberBillFragment extends BaseBillFragment {

    protected String orderType;

    @Override
    protected JsonResponse<BaseRowData<BillData>> getData() throws Exception {
        return BalanceAccountAction.getMemberBillList(storeId, orderType, startTime, endTime,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    public String getTitle() {
        return "会员";
    }
}
