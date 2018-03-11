package com.yaya.merchant.fragment.balance;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseData;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.BalanceAccountAction;
import com.yaya.merchant.base.fragment.BasePtrRecycleFragment;
import com.yaya.merchant.data.account.BalanceAccount;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.widgets.adapter.AccountBalanceGroupAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * Created by admin on 2018/3/10.
 */

public class BalanceAccountFragment extends BasePtrRecycleFragment<BalanceAccount> {

    private String storeId = "";
    private String payState = "";
    private String payType = "";
    private String startTime = "";
    private String endTime = "";

    private List<String> groupList = new ArrayList<>();
    private HashMap<String, List<BalanceAccount>> map = new HashMap<>();

    @BindView(R.id.balance_account_tv_change_status)
    protected TextView changeStatusTv;
    @BindView(R.id.balance_account_tv_screen)
    protected TextView screenTv;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_balance_account;
    }

    @Override
    protected void initData() {
        super.initData();
        getAllMerchant();
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new AccountBalanceGroupAdapter(groupList);
    }

    @Override
    protected JsonResponse<BaseRowData<BalanceAccount>> getData() throws Exception {
        return BalanceAccountAction.getAccountList(storeId, payState, payType, startTime, endTime,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
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
    protected void setData(List<BalanceAccount> dataList) {
        for (BalanceAccount data : dataList) {
            String[] payTime = data.getPayTime().split("T");
            if (map.containsKey(payTime[0])) {
                map.get(payTime[0]).add(data);
            } else {
                List<BalanceAccount> balanceList = new ArrayList<>();
                balanceList.add(data);
                map.put(payTime[0], balanceList);
            }
        }
        Iterator<String> iterator = map.keySet().iterator();
        groupList.clear();
        while (iterator.hasNext()) {
            groupList.add(iterator.next());
        }
        ((AccountBalanceGroupAdapter)adapter).setBalanceHashMap(map);
        adapter.notifyDataSetChanged();
    }

    public String getSelectedMerchantId() {
        return storeId;
    }

    public void setSelectedMerchantId(String storeId) {
        this.storeId = storeId;
        refresh();
    }

    @Override
    public String getTitle() {
        return "入账";
    }
}
