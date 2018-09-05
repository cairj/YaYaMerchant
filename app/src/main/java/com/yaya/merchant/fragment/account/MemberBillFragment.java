package com.yaya.merchant.fragment.account;

import android.text.TextUtils;

import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.activity.account.GatheringDetailActivity;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.data.account.BillData;
import com.yaya.merchant.data.account.BillDetailData;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.net.callback.GsonCallback;
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
                String.valueOf(mCurrentPos), String.valueOf(pageSize),search);
    }

    @Override
    protected MerchantBillGroupAdapter.OnItemClickListener getItemListener() {
        return new MerchantBillGroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BillDetailData billDetailData) {
                GatheringDetailActivity.open(getActivity(), String.valueOf(billDetailData.getId()), Urls.GET_MEMBER_DETAIL);
            }
        };
    }

    @Override
    protected void initView() {
        super.initView();
        changeStatusTv.setText("选择门店");
        initTimePickerWindow();
    }

    @Override
    protected void initSingleChoiceWindow() {
        super.initSingleChoiceWindow();
        getAllMerchant();
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

    private void getAllMerchant(){
        MainDataAction.searchMerchant(new GsonCallback<Merchant>(Merchant.class) {
            @Override
            public void onSucceed(JsonResponse<Merchant> response) {
                for (Merchant merchant : response.getDataList()){
                    ChoiceItem item = new ChoiceItem(merchant.getStoreName(), String.valueOf(merchant.getId()));
                    singleChoiceItemList.add(item);
                }
                singleChoiceItemList.get(0).setSelect(true);
                singleChoiceItemList.get(0).setContent("全部门店");
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
        });
    }

    @OnClick(R.id.balance_account_tv_screen)
    protected void screen(){
        timePickerWindow.showDropDown(changeStatusTv);
    }

    @Override
    public String getTitle() {
        return "门店";
    }
}
