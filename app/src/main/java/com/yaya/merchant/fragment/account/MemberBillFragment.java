package com.yaya.merchant.fragment.account;

import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.data.account.BillData;

/**
 * Created by 蔡蓉婕 on 2018/3/12.
 */

public class MemberBillFragment extends BaseBillFragment {

    protected String orderType;

    @Override
    protected JsonResponse<BaseRowData<BillData>> getData() throws Exception {
        return MainDataAction.getMemberBillList(storeId, orderType, startTime, endTime,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    public String getTitle() {
        return "会员";
    }
}
