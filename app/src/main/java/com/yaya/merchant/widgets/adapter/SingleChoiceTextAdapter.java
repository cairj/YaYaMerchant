package com.yaya.merchant.widgets.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.util.DpPxUtil;

import java.util.List;

/**
 * 只有一个文字的列表
 */

public class SingleChoiceTextAdapter extends BaseQuickAdapter<ChoiceItem> {
    public SingleChoiceTextAdapter(List<ChoiceItem> data) {
        super(R.layout.textview, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final ChoiceItem item) {
        baseViewHolder.setText(R.id.text_view, item.getContent());

        TextView tv = baseViewHolder.getView(R.id.text_view);
        ViewGroup.LayoutParams lp = tv.getLayoutParams();
        lp.height = DpPxUtil.dp2px(45);
        tv.setLayoutParams(lp);

        tv.setSelected(item.isSelect());

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(item);
                }
            }
        });
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(ChoiceItem item);
    }

}
