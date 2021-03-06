package com.yaya.merchant.widgets.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.account.BillData;
import com.yaya.merchant.util.StringsUtil;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import java.util.List;

/**
 * Created by admin on 2018/3/11.
 */

public class MerchantBillChildAdapter extends BaseQuickAdapter<BillData> {

    public MerchantBillChildAdapter(List<BillData> data) {
        super(R.layout.item_balance_account_child, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final BillData billData) {
        String[] time = billData.getPayTime().split("T");
        String payTime="";
        if (time.length>1){
            payTime = time[1];
        }
        baseViewHolder.setText(R.id.tv_pay_time, payTime)
                .setText(R.id.tv_pay_money, "￥"+StringsUtil.formatDecimals(billData.getOrderSumprice()));

        GlideLoaderHelper.loadAvatar(billData.getHeadImgUrl(), (ImageView) baseViewHolder.getView(R.id.item_iv_head));

        //右下
        TextView refundTv=baseViewHolder.getView(R.id.tv_pay_refund);
        if (!TextUtils.isEmpty(billData.getPayStatus())) {
            baseViewHolder.setText(R.id.tv_pay_status,billData.getPayStatus() );
            if (billData.getPayStatus().contains("退款")){
                refundTv.setVisibility(View.VISIBLE);
            }else {
                refundTv.setVisibility(View.GONE);
            }
        }
        if (!TextUtils.isEmpty(billData.getOrderType())) {
            baseViewHolder.setText(R.id.tv_pay_status,billData.getOrderType() );
        }

        //左上
        TextView payTypeTv = baseViewHolder.getView(R.id.tv_pay_type);
        if (!TextUtils.isEmpty(billData.getPayType())) {//入账
            if (billData.getPayType().contains("微信")) {
                payTypeTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_weixin, 0, 0, 0);
            } else if (billData.getPayType().contains("支付宝")) {
                payTypeTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_zhifubao, 0, 0, 0);
            }
            payTypeTv.setText(billData.getPayType());
        }
        if (!TextUtils.isEmpty(billData.getName())){//会员
            payTypeTv.setText(billData.getName());
        }

        baseViewHolder.getView(R.id.ll_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(billData);
            }
        });

    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(BillData billData);
    }

}
