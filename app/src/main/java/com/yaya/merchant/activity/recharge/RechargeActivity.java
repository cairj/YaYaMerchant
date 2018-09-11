package com.yaya.merchant.activity.recharge;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.RechargeAction;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.recharge.RechargeData;

/**
 * Created by 蔡蓉婕 on 2018/9/11.
 */

public class RechargeActivity extends BasePtrRecycleActivity<RechargeData.RechargeRows>{

    private String startTime;
    private String endTime;
    private String sign;
    private String payType;

    private String balance;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return null;
    }

    @Override
    protected JsonResponse<BaseRowData<RechargeData.RechargeRows>> getData() throws Exception {
        JsonResponse<RechargeData> response = RechargeAction.getRechargeRecord(startTime,endTime,sign,payType,String.valueOf(mCurrentPos),String.valueOf(pageSize));
        balance = response.getResultData().getBalance();
        JsonResponse<BaseRowData<RechargeData.RechargeRows>> clone = new JsonResponse<>();
        clone.setData(response.getResultData());
        return clone;
    }


}
