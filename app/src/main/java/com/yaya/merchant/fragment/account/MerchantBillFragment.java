package com.yaya.merchant.fragment.account;

import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.data.account.BillData;

/**
 * 入账
 */

public class MerchantBillFragment extends BaseBillFragment {

    protected String payState = "";
    protected String payType = "";

    @Override
    protected JsonResponse<BaseRowData<BillData>> getData() throws Exception {
        return MainDataAction.getAccountList(storeId, payState, payType, startTime, endTime,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void initSingleChoiceWindow() {
        super.initSingleChoiceWindow();

    }

    @Override
    public String getTitle() {
        return "入账";
    }
}
