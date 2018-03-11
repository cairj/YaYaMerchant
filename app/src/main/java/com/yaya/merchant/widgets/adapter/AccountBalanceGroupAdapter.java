package com.yaya.merchant.widgets.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.account.BalanceAccount;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.util.StringsUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2018/3/11.
 */


public class AccountBalanceGroupAdapter extends BaseQuickAdapter<String> {

    private HashMap<String, List<BalanceAccount>> balanceHashMap;//每个日期下对应的账单
    private RecyclerView.ItemDecoration decoration;

    public AccountBalanceGroupAdapter(List<String> data) {
        super(R.layout.item_balance_account_group, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String date) {
        baseViewHolder.setText(R.id.item_tv_date, date);
        baseViewHolder.setText(R.id.item_tv_statistics, statisticData(balanceHashMap.get(date)));

        RecyclerView recyclerView = baseViewHolder.getView(R.id.item_rv_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        addItemDecoration(recyclerView);
        AccountBalanceChildAdapter childAdapter = new AccountBalanceChildAdapter(balanceHashMap.get(date));
        recyclerView.setAdapter(childAdapter);
        /*//子列表的高度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        lp.height = balanceHashMap.get(date).size() * DpPxUtil.dp2px(64);
        recyclerView.setLayoutParams(lp);*/
    }

    public void setBalanceHashMap(HashMap<String, List<BalanceAccount>> balanceHashMap) {
        this.balanceHashMap = balanceHashMap;
    }

    protected void addItemDecoration(RecyclerView recyclerView) {
        if(decoration == null) {
            decoration = new HorizontalDividerItemDecoration.Builder(mContext)
                    .color(ContextCompat.getColor(mContext, R.color.gray_F6F7F9))
                    .sizeResId(R.dimen.divider_height)
                /*.marginResId(R.dimen.margin_edge)*/
                    .build();
        }
        recyclerView.removeItemDecoration(decoration);
        recyclerView.addItemDecoration(decoration);
    }

    private String statisticData(List<BalanceAccount> list) {
        float totalMoney = 0;
        for (BalanceAccount balanceAccount : list) {
            totalMoney += balanceAccount.getOrderSumprice();
        }
        return String.format("共%d笔，￥%s", list.size(), StringsUtil.formatDecimals(totalMoney));
    }

}
