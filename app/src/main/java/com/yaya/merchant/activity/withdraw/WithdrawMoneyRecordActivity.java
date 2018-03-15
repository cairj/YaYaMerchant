package com.yaya.merchant.activity.withdraw;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.WithDrawMoneyAction;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.withdraw.WithdrawMoneyRecord;
import com.yaya.merchant.widgets.adapter.WithdrawMoneyAdapter;

/**
 * Created by 蔡蓉婕 on 2018/3/15.
 */

public class WithdrawMoneyRecordActivity extends BasePtrRecycleActivity<WithdrawMoneyRecord> {

    private String cashoutType;
    private String status;
    private String startTime;
    private String endTime;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_withdraw_record;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new WithdrawMoneyAdapter(mDataList);
    }

    @Override
    protected JsonResponse<BaseRowData<WithdrawMoneyRecord>> getData() throws Exception {
        return WithDrawMoneyAction.getWithdrawMoneyRecord(cashoutType,status,startTime,endTime,
                String.valueOf(mCurrentPos),String.valueOf(pageSize));
    }
}
