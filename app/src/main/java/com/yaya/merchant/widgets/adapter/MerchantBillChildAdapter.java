package com.yaya.merchant.widgets.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.account.BillDetailData;
import com.yaya.merchant.util.StringsUtil;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import java.util.List;

/**
 * Created by admin on 2018/3/11.
 */

public class MerchantBillChildAdapter extends BaseQuickAdapter<BillDetailData> {

    public MerchantBillChildAdapter(List<BillDetailData> data) {
        super(R.layout.item_balance_account_child, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final BillDetailData billDetailData) {
        baseViewHolder.setText(R.id.tv_pay_time, billDetailData.getPayTime())
                .setText(R.id.tv_pay_money, "￥" + StringsUtil.formatDecimals(billDetailData.getOrderSumprice()));

        GlideLoaderHelper.loadAvatar(billDetailData.getHeadImgUrl(), (ImageView) baseViewHolder.getView(R.id.item_iv_head));

        //右下
        TextView refundTv = baseViewHolder.getView(R.id.tv_pay_refund);
        if (!TextUtils.isEmpty(billDetailData.getPayStatus())) {
            baseViewHolder.setText(R.id.tv_pay_status, billDetailData.getPayStatus());
            if (billDetailData.getPayStatus().contains("退款")) {
                refundTv.setVisibility(View.VISIBLE);
            } else {
                refundTv.setVisibility(View.GONE);
            }
        }
        if (!TextUtils.isEmpty(billDetailData.getPayType())) {
            for (int i = 0; i < BillDetailData.PAY_TYPE_PARAMS.length; i++) {
                if (BillDetailData.PAY_TYPE_PARAMS[i].equals(billDetailData.getPayType())) {
                    baseViewHolder.setText(R.id.tv_pay_status, BillDetailData.PAY_TYPE[i]);
                }
            }
        }

        ImageView orderTypeIv = baseViewHolder.getView(R.id.item_iv_order_type);
        switch (billDetailData.getOrderType()) {
            case BillDetailData.ORDER_TYPE_ONLINE_PARAM:
                orderTypeIv.setVisibility(View.VISIBLE);
                orderTypeIv.setImageResource(R.mipmap.ic_order_online);
                break;
            case BillDetailData.ORDER_TYPE_CASH_PARAM:
                orderTypeIv.setVisibility(View.VISIBLE);
                orderTypeIv.setImageResource(R.mipmap.ic_order_recharge);
                break;
            case BillDetailData.ORDER_TYPE_OFFLINE_PARAM:
                orderTypeIv.setVisibility(View.VISIBLE);
                orderTypeIv.setImageResource(R.mipmap.ic_order_offline);
                break;
            default:
                orderTypeIv.setVisibility(View.GONE);
        }

        //左上
        TextView payTypeTv = baseViewHolder.getView(R.id.tv_pay_type);
        /*if (!TextUtils.isEmpty(billDetailData.getPayType())) {//入账
            if (billDetailData.getPayType().contains("微信")) {
                payTypeTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_weixin, 0, 0, 0);
            } else if (billDetailData.getPayType().contains("支付宝")) {
                payTypeTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_zhifubao, 0, 0, 0);
            }
            payTypeTv.setText(billDetailData.getPayType());
        }*/
        if (!TextUtils.isEmpty(billDetailData.getName())) {//会员
            payTypeTv.setText(billDetailData.getName());
        }

        baseViewHolder.getView(R.id.ll_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(billDetailData);
            }
        });

    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(BillDetailData billDetailData);
    }

}
