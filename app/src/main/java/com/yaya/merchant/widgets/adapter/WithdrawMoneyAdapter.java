package com.yaya.merchant.widgets.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.withdraw.WithdrawMoneyRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WithdrawMoneyAdapter extends BaseQuickAdapter<WithdrawMoneyRecord> {
    public WithdrawMoneyAdapter(List<WithdrawMoneyRecord> data) {
        super(R.layout.item_withdraw_record, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WithdrawMoneyRecord withdrawMoneyRecord) {
        baseViewHolder.setText(R.id.tv_amount, "￥" + withdrawMoneyRecord.getAmount())
                .setText(R.id.tv_way, withdrawMoneyRecord.getCashoutType())
                .setText(R.id.tv_poundage, "￥" + withdrawMoneyRecord.getCashoutFee())

                .setText(R.id.tv_bank_card, withdrawMoneyRecord.getBankName()
                        +"("+ withdrawMoneyRecord.getAccountNumber().substring(withdrawMoneyRecord.getAccountNumber().length() - 4)+")")
                .setText(R.id.tv_serial_number, withdrawMoneyRecord.getCashoutNo());

        Date date = new Date(Integer.valueOf(withdrawMoneyRecord.getCreationTime()));
        SimpleDateFormat sp = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        baseViewHolder.setText(R.id.tv_time, sp.format(date));

        switch (withdrawMoneyRecord.getStatus()) {
            case WithdrawMoneyRecord.CASH_OUT_STATUS_SUCCESS_VALUE:
                baseViewHolder.setText(R.id.tv_status, WithdrawMoneyRecord.CASH_OUT_STATUS_SUCCESS);
                break;
            case WithdrawMoneyRecord.CASH_OUT_STATUS_FAILURE_VALUE:
                baseViewHolder.setText(R.id.tv_status, WithdrawMoneyRecord.CASH_OUT_STATUS_FAILURE);
                break;
            case WithdrawMoneyRecord.CASH_OUT_STATUS_IN_PROGRESS_VALUE:
                baseViewHolder.setText(R.id.tv_status, WithdrawMoneyRecord.CASH_OUT_STATUS_IN_PROGRESS);
                break;
        }
    }
}
