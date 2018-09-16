package com.yaya.merchant.activity.recharge;

import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.RechargeAction;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.data.recharge.RechargeData;
import com.yaya.merchant.fragment.account.MerchantBillFragment;
import com.yaya.merchant.widgets.adapter.RechargeAdapter;
import com.yaya.merchant.widgets.adapter.SingleChoiceTextAdapter;
import com.yaya.merchant.widgets.popupwindow.SingleChoiceWindow;
import com.yaya.merchant.widgets.popupwindow.screenwindow.MerchantBillScreenWindow;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 蔡蓉婕 on 2018/9/11.
 */

public class RechargeActivity extends BasePtrRecycleActivity<RechargeData.RechargeRows>{

    private String startTime;
    private String endTime;
    private String sign;
    private String payType;

    private String balance;

    @BindView(R.id.tv_balance)
    protected TextView balanceTv;
    @BindView(R.id.tv_change_status)
    protected TextView changeStatusTv;
    @BindView(R.id.tv_screen)
    protected TextView screenTv;

    protected SingleChoiceWindow singleChoiceWindow;
    protected SingleChoiceTextAdapter singleChoiceAdapter;
    protected ArrayList<ChoiceItem> singleChoiceItemList = new ArrayList<>();

    protected MerchantBillScreenWindow screenWindow;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_recharge;
    }


    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("充值");
    }

    @Override
    protected void initData() {
        super.initData();
        initSingleChoiceWindow();
        initScreenWindow();
    }

    protected void initSingleChoiceWindow(){
        singleChoiceWindow=new SingleChoiceWindow(this);
        singleChoiceAdapter = new SingleChoiceTextAdapter(singleChoiceItemList);
        singleChoiceWindow.setAdapter(singleChoiceAdapter);
        singleChoiceWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeStatusTv.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_down,0);
            }
        });
        for (int i = 0; i < RechargeData.RechargeRows.SIGN_TYPE_VALUE.length; i++) {
            ChoiceItem item = new ChoiceItem(RechargeData.RechargeRows.SIGN_TYPE_VALUE[i],
                    RechargeData.RechargeRows.SIGN_TYPE_VALUE_PARAMS[i]);
            singleChoiceItemList.add(item);
        }
        singleChoiceItemList.get(0).setContent("全部类型");
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
                sign = item.getId();
                refresh();
                singleChoiceWindow.dismiss();
            }
        });
    }

    private void initScreenWindow() {
        screenWindow = new MerchantBillScreenWindow(this);
        screenWindow.setListener(new MerchantBillScreenWindow.OnSubmitListener() {
            @Override
            public void submit(String startTime, String endTime, String state) {
                RechargeActivity.this.startTime = startTime;
                RechargeActivity.this.endTime = endTime;
                payType = state;
                refresh();
            }
        });
    }

    @OnClick({R.id.tv_change_status,R.id.tv_screen})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.tv_change_status:
                singleChoiceWindow.showDropDown(changeStatusTv);
                changeStatusTv.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_up,0);
                break;
            case R.id.tv_screen:
                screenWindow.showDropDown(changeStatusTv);
                break;
        }
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new RechargeAdapter(mDataList);
    }

    @Override
    protected JsonResponse<BaseRowData<RechargeData.RechargeRows>> getData() throws Exception {
        JsonResponse<RechargeData> response = RechargeAction.getRechargeRecord(startTime,endTime,sign,payType,String.valueOf(mCurrentPos),String.valueOf(pageSize));
        balance = response.getResultData().getBalance();
        JsonResponse<BaseRowData<RechargeData.RechargeRows>> clone = new JsonResponse<>();
        clone.setData(response.getResultData());
        clone.setCode(response.getCode());
        return clone;
    }

    @Override
    protected void onLoadJsonResponse(JsonResponse<BaseRowData<RechargeData.RechargeRows>> ts) {
        super.onLoadJsonResponse(ts);
        balanceTv.setText(balance);
    }
}
