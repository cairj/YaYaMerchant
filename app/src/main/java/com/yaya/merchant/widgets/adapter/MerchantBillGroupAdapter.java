package com.yaya.merchant.widgets.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.account.BillData;
import com.yaya.merchant.util.StringsUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/3/11.
 */


public class MerchantBillGroupAdapter extends BaseQuickAdapter<String> {

    private Map<String, List<BillData>> balanceHashMap;//每个日期下对应的账单
    private RecyclerView.ItemDecoration decoration;

    public MerchantBillGroupAdapter(List<String> data) {
        super(R.layout.item_balance_account_group, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String date) {
        baseViewHolder.setText(R.id.item_tv_date, date);
        baseViewHolder.setText(R.id.item_tv_statistics, statisticData(balanceHashMap.get(date)));

        RecyclerView recyclerView = baseViewHolder.getView(R.id.item_rv_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        addItemDecoration(recyclerView);
        MerchantBillChildAdapter childAdapter = new MerchantBillChildAdapter(balanceHashMap.get(date));
        childAdapter.setListener(new MerchantBillChildAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BillData billData) {
                if (listener != null){
                    listener.onItemClick(billData);
                }
            }
        });
        recyclerView.setAdapter(childAdapter);

        recyclerView.setFocusableInTouchMode(false);
        recyclerView.requestFocus();
    }

    public void setBalanceHashMap(Map<String, List<BillData>> balanceHashMap) {
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

    private String statisticData(List<BillData> list) {
        float totalMoney = 0;
        for (BillData billData : list) {
            totalMoney += billData.getOrderSumprice();
        }
        return String.format("共%d笔，￥%s", list.size(), StringsUtil.formatDecimals(totalMoney));
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(BillData billData);
    }
}
