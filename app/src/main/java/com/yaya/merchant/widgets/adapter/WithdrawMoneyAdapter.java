package com.yaya.merchant.widgets.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.withdraw.WithdrawMoneyRecord;

import java.util.List;

public class WithdrawMoneyAdapter extends BaseQuickAdapter<WithdrawMoneyRecord> {
    public WithdrawMoneyAdapter(List<WithdrawMoneyRecord> data) {
        super(R.layout.item_withdraw_record, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WithdrawMoneyRecord withdrawMoneyRecord) {
        baseViewHolder.setText(R.id.tv_status, withdrawMoneyRecord.getStatus())
                .setText(R.id.tv_amount, "￥" + withdrawMoneyRecord.getAmount())
                .setText(R.id.tv_way, withdrawMoneyRecord.getCashoutType())
                .setText(R.id.tv_poundage, "￥" + withdrawMoneyRecord.getCashoutFee())

                .setText(R.id.tv_bank_card, withdrawMoneyRecord.getBankName())
                .setText(R.id.tv_serial_number, withdrawMoneyRecord.getCashoutNo());

        if (!TextUtils.isEmpty(withdrawMoneyRecord.getCreationTime())) {
            String[] date = withdrawMoneyRecord.getCreationTime().split("T");
            baseViewHolder.setText(R.id.tv_time, date[0] + " " + date[1]);
        }

    }
}
