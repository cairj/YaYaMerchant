package com.yaya.merchant.widgets.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.recharge.RechargeData;

import java.util.List;

/**
 * Created by admin on 2018/9/15.
 */

public class RechargeAdapter extends BaseQuickAdapter<RechargeData.RechargeRows> {
    public RechargeAdapter(List<RechargeData.RechargeRows> data) {
        super(R.layout.item_recharge_record, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, RechargeData.RechargeRows rechargeRows) {
        baseViewHolder.setText(R.id.tv_name, rechargeRows.getNickName())
                .setText(R.id.tv_order_num, rechargeRows.getOrderNo())
                .setText(R.id.tv_status, rechargeRows.getSignType());

        TextView priceTv = baseViewHolder.getView(R.id.tv_price);
        switch (rechargeRows.getSign()) {
            case RechargeData.RechargeRows.SIGN_TYPE_RECHARGE:
                priceTv.setText("+" + rechargeRows.getBalance());
                priceTv.setTextColor(ContextCompat.getColor(mContext, R.color.red_f94745));
                break;
            case RechargeData.RechargeRows.SIGN_TYPE_CONSUME:
                priceTv.setText("-" + rechargeRows.getBalance());
                priceTv.setTextColor(ContextCompat.getColor(mContext, R.color.green_10A700));
                break;
        }
    }
}
