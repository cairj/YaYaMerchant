package com.yaya.merchant.widgets.adapter;

import android.view.View;
import android.widget.ToggleButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.account.Merchant;

import java.util.List;

/**
 * Created by admin on 2018/3/11.
 */

public class SearchMerchantAdapter extends BaseQuickAdapter<Merchant> {

    public SearchMerchantAdapter(List<Merchant> data) {
        super(R.layout.item_search_result, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final Merchant merchant) {
        baseViewHolder.setText(R.id.tv_merchant_name, merchant.getStoreName())
                .setText(R.id.tv_merchant_address, "");

        ToggleButton toggleButton = baseViewHolder.getView(R.id.item_search_tg_btn);
        toggleButton.setChecked(merchant.isSelected());

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(merchant);
                }
            }
        };

        baseViewHolder.getView(R.id.rl_item_parent).setOnClickListener(onClickListener);
        toggleButton.setOnClickListener(onClickListener);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(Merchant merchant);
    }

}
