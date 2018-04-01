package com.yaya.merchant.fragment.account;

import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.activity.account.GatheringDetailActivity;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.data.account.BillData;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.widgets.adapter.MerchantBillGroupAdapter;
import com.yaya.merchant.widgets.adapter.SingleChoiceTextAdapter;
import com.yaya.merchant.widgets.popupwindow.screenwindow.TimePickerWindow;

import butterknife.OnClick;

/**
 * Created by 蔡蓉婕 on 2018/3/12.
 */

public class MemberBillFragment extends BaseBillFragment {

    protected String orderType;
    private TimePickerWindow timePickerWindow;

    @Override
    protected JsonResponse<BaseRowData<BillData>> getData() throws Exception {
        return MainDataAction.getMemberBillList(storeId, orderType, startTime, endTime,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected MerchantBillGroupAdapter.OnItemClickListener getItemListener() {
        return new MerchantBillGroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BillData billData) {
                GatheringDetailActivity.open(getActivity(), String.valueOf(billData.getId()), Urls.GET_MEMBER_DETAIL);
            }
        };
    }

    @Override
    protected void initView() {
        super.initView();
        initTimePickerWindow();
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

    private void initTimePickerWindow() {
        timePickerWindow = new TimePickerWindow(getActivity());
        timePickerWindow.setHintTvVisible(true);
        timePickerWindow.setPhoneLLVisible(true);
        timePickerWindow.setListener(new TimePickerWindow.OnSubmitListener() {
            @Override
            public void submit(String startTime, String endTime, String phone) {
                MemberBillFragment.this.startTime = startTime;
                MemberBillFragment.this.endTime = endTime;
                search = phone;
                refresh();
            }
        });
    }

    @OnClick(R.id.balance_account_tv_screen)
    protected void screen(){
        timePickerWindow.showDropDown(changeStatusTv);
    }

    @Override
    public String getTitle() {
        return "会员";
    }
}
