package com.yaya.merchant.widgets.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.user.MerchantData;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import java.util.List;

/**
 * 门店适配器
 */

public class MerchantManagerAdapter extends BaseQuickAdapter<MerchantData> {
    public MerchantManagerAdapter(List<MerchantData> data) {
        super(R.layout.item_merchant_manager, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final MerchantData merchantData) {
        baseViewHolder.setText(R.id.tv_merchant_name, merchantData.getName())
                .setText(R.id.tv_merchant_address, merchantData.getAddress());

        //GlideLoaderHelper.loadImg(merchantData.getLogo(), (ImageView) baseViewHolder.getView(R.id.iv_logo));

        baseViewHolder.getView(R.id.ll_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onClick(merchantData);
                }
            }
        });
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onClick(MerchantData merchantData);
    }

}
