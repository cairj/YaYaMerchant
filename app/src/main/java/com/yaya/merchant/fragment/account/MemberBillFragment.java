package com.yaya.merchant.fragment.account;

import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.data.account.BillData;
import com.yaya.merchant.widgets.adapter.SingleChoiceTextAdapter;

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
    protected void initSingleChoiceWindow() {
        super.initSingleChoiceWindow();
        for (int i = 0; i < BillData.ORDER_TYPE.length; i++) {
            ChoiceItem item = new ChoiceItem(BillData.ORDER_TYPE[i], BillData.ORDER_TYPE[i]);
            singleChoiceItemList.add(item);
        }
        singleChoiceItemList.get(0).setSelect(true);
        singleChoiceItemList.get(0).setContent("全部方式");
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
                orderType = item.getId();
                refresh();
                singleChoiceWindow.dismiss();
            }
        });
    }

    @Override
    public String getTitle() {
        return "会员";
    }
}
