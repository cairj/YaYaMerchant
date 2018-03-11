package com.yaya.merchant.widgets.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.account.BalanceAccount;
import com.yaya.merchant.util.StringsUtil;

import java.util.List;

/**
 * Created by admin on 2018/3/11.
 */

public class AccountBalanceChildAdapter extends BaseQuickAdapter<BalanceAccount> {

    public AccountBalanceChildAdapter(List<BalanceAccount> data) {
        super(R.layout.item_balance_account_child, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, BalanceAccount balanceAccount) {
        String[] time = balanceAccount.getPayTime().split("T");
        String payTime="";
        if (time.length>1){
            payTime = time[1];
        }
        baseViewHolder.setText(R.id.tv_pay_money, StringsUtil.formatDecimals(balanceAccount.getOrderSumprice()))
                .setText(R.id.tv_pay_type, balanceAccount.getPayType()).setText(R.id.tv_pay_time, payTime);
        baseViewHolder.setText(R.id.tv_pay_status, balanceAccount.getPayStatus());

        TextView payTypeTv = baseViewHolder.getView(R.id.tv_pay_type);
        if (balanceAccount.getPayType().contains("微信")){
            payTypeTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_weixin,0,0,0);
        }else if (balanceAccount.getPayType().contains("支付宝")){
            payTypeTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_zhifubao,0,0,0);
        }
    }
}
