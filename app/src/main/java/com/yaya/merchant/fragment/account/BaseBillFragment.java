package com.yaya.merchant.fragment.account;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.BalanceAccountAction;
import com.yaya.merchant.base.fragment.BasePtrRecycleFragment;
import com.yaya.merchant.data.account.BillData;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.widgets.adapter.MerchantBillGroupAdapter;
import com.yaya.merchant.widgets.adapter.SingleChoiceTextAdapter;
import com.yaya.merchant.widgets.popupwindow.SingleChoiceWindow;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 入账
 */

public abstract class BaseBillFragment extends BasePtrRecycleFragment<BillData> {

    protected String storeId = "";
    protected String startTime = "";
    protected String endTime = "";

    protected List<String> groupList = new ArrayList<>();
    protected TreeMap<String, List<BillData>> map = new TreeMap<>();

    @BindView(R.id.balance_account_tv_change_status)
    protected TextView changeStatusTv;
    @BindView(R.id.balance_account_tv_screen)
    protected TextView screenTv;

    protected SingleChoiceWindow singleChoiceWindow;
    protected SingleChoiceTextAdapter singleChoiceAdapter;
    protected ArrayList<String> singleChoiceItemList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_balance_account;
    }

    @Override
    protected void initView() {
        super.initView();
        initSingleChoiceWindow();
        changeStatusTv.setText("全部状态");
    }

    protected void initSingleChoiceWindow(){
        singleChoiceWindow=new SingleChoiceWindow();
        singleChoiceAdapter = new SingleChoiceTextAdapter(singleChoiceItemList);
        singleChoiceWindow.setAdapter(singleChoiceAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        getAllMerchant();
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MerchantBillGroupAdapter(groupList);
    }

    private void getAllMerchant(){
        Type type=new TypeToken<BaseRowData<Merchant>>(){}.getType();
        BalanceAccountAction.searchMerchant("", new GsonCallback<BaseRowData<Merchant>>(type) {
            @Override
            public void onSucceed(JsonResponse<BaseRowData<Merchant>> response) {
                storeId = "";
                for (Merchant merchant : (response.getData().getData()).getRows()){
                    storeId = (TextUtils.isEmpty(storeId) ? "" : storeId + ",") + merchant.getId();
                }
                refresh();
            }
        });
    }

    @Override
    protected void setData(List<BillData> dataList) {
        for (BillData data : dataList) {
            String[] payTime = data.getPayTime().split("T");
            if (map.containsKey(payTime[0])) {
                map.get(payTime[0]).add(data);
            } else {
                List<BillData> balanceList = new ArrayList<>();
                balanceList.add(data);
                map.put(payTime[0], balanceList);
            }
        }
        Iterator<String> iterator = map.keySet().iterator();
        groupList.clear();
        while (iterator.hasNext()) {
            groupList.add(iterator.next());
        }
        ((MerchantBillGroupAdapter)adapter).setBalanceHashMap(map);
        adapter.notifyDataSetChanged();
    }

    public String getSelectedMerchantId() {
        return storeId;
    }

    public void setSelectedMerchantId(String storeId) {
        this.storeId = storeId;
        refresh();
    }

    @OnClick(R.id.balance_account_tv_change_status)
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.balance_account_tv_change_status:
                singleChoiceWindow.show(changeStatusTv);
                break;
        }
    }

}
