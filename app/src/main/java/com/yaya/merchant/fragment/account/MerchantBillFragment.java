package com.yaya.merchant.fragment.account;

import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.activity.account.GatheringDetailActivity;
import com.yaya.merchant.activity.account.RefundDetailActivity;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.data.account.BillData;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.widgets.adapter.MerchantBillGroupAdapter;
import com.yaya.merchant.widgets.adapter.SingleChoiceTextAdapter;
import com.yaya.merchant.widgets.popupwindow.screenwindow.MerchantBillScreenWindow;

import java.util.Collections;

import butterknife.OnClick;

/**
 * 入账
 */

public class MerchantBillFragment extends BaseBillFragment {

    protected String payState = "";
    protected String payType = "";

    MerchantBillScreenWindow screenWindow;

    @Override
    protected void initView() {
        super.initView();
        changeStatusTv.setText("订单类型");
        initScreenWindow();
    }

    @Override
    protected JsonResponse<BaseRowData<BillData>> getData() throws Exception {
        return MainDataAction.getAccountList(storeId, payState, payType, startTime, endTime,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected MerchantBillGroupAdapter.OnItemClickListener getItemListener() {
        return new MerchantBillGroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BillData billData) {
                if (billData.getPayStatus().contains("退款")){
                    RefundDetailActivity.open(getActivity(),String.valueOf(billData.getId()));
                }else {
                    GatheringDetailActivity.open(getActivity(),String.valueOf(billData.getId()), Urls.GET_HOUSTON_DETAIL);
                }
            }
        };
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

    private void initScreenWindow() {
        screenWindow = new MerchantBillScreenWindow(getActivity());
        screenWindow.setListener(new MerchantBillScreenWindow.OnSubmitListener() {
            @Override
            public void submit(String startTime, String endTime, String state) {
                MerchantBillFragment.this.startTime = startTime;
                MerchantBillFragment.this.endTime = endTime;
                payType = state;
                refresh();
            }
        });
    }

    @OnClick(R.id.balance_account_tv_screen)
    protected void screen(){
        screenWindow.showDropDown(changeStatusTv);
    }


    @Override
    public String getTitle() {
        return "入账";
    }
}
