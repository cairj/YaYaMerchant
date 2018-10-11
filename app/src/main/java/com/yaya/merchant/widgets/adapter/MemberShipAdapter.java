package com.yaya.merchant.widgets.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.membership.MemberShipData;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import java.util.List;

/**
 * 门店适配器
 */

public class MemberShipAdapter extends BaseQuickAdapter<MemberShipData> {

    public MemberShipAdapter(List<MemberShipData> data) {
        super(R.layout.item_balance_account_child, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final MemberShipData memberShipData) {
            baseViewHolder.setText(R.id.tv_pay_type, memberShipData.getUserName())
                    .setText(R.id.tv_pay_money, memberShipData.getLevelName())
                    .setText(R.id.tv_pay_time,memberShipData.getUserTel())
                    .setText(R.id.tv_pay_status,memberShipData.getBalance());

        GlideLoaderHelper.loadImg(memberShipData.getUserHeadImg(), (ImageView) baseViewHolder.getView(R.id.item_iv_head));

        baseViewHolder.getView(R.id.ll_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onClick(memberShipData);
                }
            }
        });
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onClick(MemberShipData merchantData);
    }

}
