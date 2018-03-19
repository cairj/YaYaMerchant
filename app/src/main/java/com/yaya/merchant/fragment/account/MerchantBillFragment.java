package com.yaya.merchant.fragment.account;

import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.data.account.BillData;
import com.yaya.merchant.widgets.adapter.SingleChoiceTextAdapter;

import java.util.Collections;

/**
 * 入账
 */

public class MerchantBillFragment extends BaseBillFragment {

    protected String payState = "";
    protected String payType = "";

    @Override
    protected void initView() {
        super.initView();
        changeStatusTv.setText("订单类型");
    }

    @Override
    protected JsonResponse<BaseRowData<BillData>> getData() throws Exception {
        return MainDataAction.getAccountList(storeId, payState, payType, startTime, endTime,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void initSingleChoiceWindow() {
        super.initSingleChoiceWindow();
        for (int i = 0; i < BillData.PAY_STATE.length; i++) {
            ChoiceItem item = new ChoiceItem(BillData.PAY_STATE[i], BillData.PAY_STATE_PARAMS[i]);
            singleChoiceItemList.add(item);
        }
        singleChoiceItemList.get(0).setSelect(true);
        singleChoiceAdapter.notifyDataSetChanged();

        singleChoiceAdapter.setListener(new SingleChoiceTextAdapter.OnItemClickListener() {
            @Override
            public void onClick(ChoiceItem item) {
                for (ChoiceItem choiceItem : singleChoiceItemList) {
                    choiceItem.setSelect(false);
                }
                item.setSelect(true);
                singleChoiceAdapter.notifyDataSetChanged();
                changeStatusTv.setText(item.getContent());
                payState = item.getId();
                refresh();
                singleChoiceWindow.dismiss();
            }
        });
    }

    @Override
    public String getTitle() {
        return "入账";
    }
}
