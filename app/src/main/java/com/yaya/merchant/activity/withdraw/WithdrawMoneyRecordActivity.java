package com.yaya.merchant.activity.withdraw;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.action.WithDrawMoneyAction;
import com.yaya.merchant.activity.login.RegisterMerchantActivity;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.data.withdraw.WithdrawMoneyRecord;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.DialogUtil;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.widgets.adapter.SingleChoiceTextAdapter;
import com.yaya.merchant.widgets.adapter.WithdrawMoneyAdapter;
import com.yaya.merchant.widgets.dialog.DoubleBtnDialog;
import com.yaya.merchant.widgets.popupwindow.SingleChoiceWindow;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 蔡蓉婕 on 2018/3/15.
 */

public class WithdrawMoneyRecordActivity extends BasePtrRecycleActivity<WithdrawMoneyRecord> {

    public static final int CASH_OUT_TYPE = 1;
    public static final int CASH_OUT_STATUS = 2;

    private String cashoutType;
    private String status;
    private String startTime;
    private String endTime;

    @BindView(R.id.tv_cash_out_way)
    protected TextView cashOutWayTv;
    @BindView(R.id.tv_choose_time)
    protected TextView chooseTimeTv;
    @BindView(R.id.tv_cash_out_status)
    protected TextView cashOutStatusTv;

    private SingleChoiceWindow cashOutTypeChoiceWindow;
    protected SingleChoiceTextAdapter cashOutTypeChoiceAdapter;
    protected ArrayList<ChoiceItem> cashOutTypeChoiceItemList = new ArrayList<>();

    private SingleChoiceWindow statusChoiceWindow;
    protected SingleChoiceTextAdapter statusChoiceAdapter;
    protected ArrayList<ChoiceItem> statusChoiceItemList = new ArrayList<>();

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
        return WithDrawMoneyAction.getWithdrawMoneyRecord(cashoutType, status, startTime, endTime,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void addItemDecoration() {
        RecyclerView.ItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.gray_F6F7F9))
                .size(DpPxUtil.dp2px(20))
                .marginResId(R.dimen.margin_edge)
                .build();
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("提现记录");
        setActionBarRight("客服");
        initSingleChoiceWindow();
    }

    //初始化选择框
    protected void initSingleChoiceWindow() {
        //提现方式选择框
        cashOutTypeChoiceWindow = new SingleChoiceWindow(this);
        cashOutTypeChoiceAdapter = new SingleChoiceTextAdapter(cashOutTypeChoiceItemList);
        cashOutTypeChoiceWindow.setAdapter(cashOutTypeChoiceAdapter);
        cashOutTypeChoiceWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cashOutWayTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_down, 0);
            }
        });
        initWindowList(WithdrawMoneyRecord.CASH_OUT_TYPE, WithdrawMoneyRecord.CASH_OUT_TYPE, "全部方式",
                cashOutTypeChoiceItemList, cashOutTypeChoiceAdapter, cashOutWayTv, cashOutTypeChoiceWindow, CASH_OUT_TYPE);

        //提现状态选择框
        statusChoiceWindow = new SingleChoiceWindow(this);
        statusChoiceAdapter = new SingleChoiceTextAdapter(statusChoiceItemList);
        statusChoiceWindow.setAdapter(statusChoiceAdapter);
        statusChoiceWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cashOutStatusTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_down, 0);
            }
        });
        initWindowList(WithdrawMoneyRecord.CASH_OUT_STATUS, WithdrawMoneyRecord.CASH_OUT_STATUS, "所有状态",
                statusChoiceItemList, statusChoiceAdapter, cashOutStatusTv, statusChoiceWindow, CASH_OUT_STATUS);

    }

    private void initWindowList(String[] dataContent, String[] dataId, String firstContent,
                                final ArrayList<ChoiceItem> itemList, final SingleChoiceTextAdapter adapter,
                                final TextView chooseTv, final SingleChoiceWindow window, final int type) {
        for (int i = 0; i < dataContent.length; i++) {
            ChoiceItem item = new ChoiceItem(dataContent[i], dataId[i]);
            itemList.add(item);
        }
        itemList.get(0).setSelect(true);
        itemList.get(0).setContent(firstContent);
        adapter.notifyDataSetChanged();
        adapter.setListener(new SingleChoiceTextAdapter.OnItemClickListener() {
            @Override
            public void onClick(ChoiceItem item) {
                for (ChoiceItem choiceItem : itemList) {
                    choiceItem.setSelect(false);
                }
                item.setSelect(true);
                adapter.notifyDataSetChanged();
                chooseTv.setText(item.getContent());
                switch (type) {
                    case CASH_OUT_TYPE:
                        cashoutType = item.getId();
                        break;
                    case CASH_OUT_STATUS:
                        status = item.getId();
                        break;
                }

                refresh();
                window.dismiss();
            }
        });
    }

    @OnClick({R.id.fl_cash_out_way, R.id.fl_cash_out_status})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_cash_out_way:
                cashOutTypeChoiceWindow.showDropDown(cashOutWayTv);
                cashOutWayTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_up, 0);
                break;
            case R.id.fl_cash_out_status:
                statusChoiceWindow.showDropDown(cashOutWayTv);
                cashOutStatusTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_up, 0);
                break;
        }
    }

    @Override
    protected void rightClick() {
        getServicePhone();
    }

    //获取客服电话
    private void getServicePhone() {
        DialogUtil.chatToService(this,"预计24小时到账\n");
    }

}
