package com.yaya.merchant.widgets.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;

import java.util.List;

/**
 * 只有一个文字的列表
 */

public class SingleChoiceTextAdapter extends BaseQuickAdapter<String> {
    public SingleChoiceTextAdapter(List<String> data) {
        super(R.layout.textview, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final String s) {
        baseViewHolder.setText(R.id.text_view, s);

        baseViewHolder.getView(R.id.text_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(s);
                }
            }
        });
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(String s);
    }

}
