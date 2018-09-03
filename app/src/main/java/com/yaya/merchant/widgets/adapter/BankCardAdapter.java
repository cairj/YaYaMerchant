package com.yaya.merchant.widgets.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.withdraw.BankCard;

import java.util.List;

/**
 * Created by 蔡蓉婕 on 2018/9/3.
 */

public class BankCardAdapter extends BaseQuickAdapter<BankCard.BankAccount> {
    public BankCardAdapter(List<BankCard.BankAccount> data) {
        super(R.layout.item_bank_account, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final BankCard.BankAccount bankAccount) {
        baseViewHolder.setText(R.id.tv_bank_name, bankAccount.getBankName())
                .setText(R.id.tv_shop_name, bankAccount.getRealName())
                .setText(R.id.tv_bank_number, "****  ****  ****  " + bankAccount.getAccountNumber().substring(bankAccount.getAccountNumber().length() - 4))
                .setImageResource(R.id.iv_selected, bankAccount.getIsDefault() == 1 ? R.mipmap.ic_tick_selected : R.mipmap.ic_tick_unseleted);

        baseViewHolder.getView(R.id.ll_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onItemClick(bankAccount);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int positions) {
        super.onBindViewHolder(holder, positions);
        LinearLayout bankInfoLL = holder.itemView.findViewById(R.id.ll_bank_info);
        if (positions % 2 != 0) {//基数背景时红的，偶数是绿的
            bankInfoLL.setBackgroundResource(R.mipmap.bg_bank_account_red);
        }else {
            bankInfoLL.setBackgroundResource(R.mipmap.bg_bank_account_green);
        }
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(BankCard.BankAccount bankAccount);
    }
}
