package com.yaya.merchant.widgets.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.order.ExpressCompany;
import com.yaya.merchant.util.DpPxUtil;

import java.util.List;

/**
 * Created by admin on 2018/3/25.
 */

public class CompanyChoiceAdapter extends BaseQuickAdapter<ExpressCompany> {
    public CompanyChoiceAdapter(List<ExpressCompany> data) {
        super(R.layout.textview, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final ExpressCompany expressCompany) {
        baseViewHolder.setText(R.id.text_view, expressCompany.getExpressName());

        TextView tv = baseViewHolder.getView(R.id.text_view);
        ViewGroup.LayoutParams lp = tv.getLayoutParams();
        lp.height = DpPxUtil.dp2px(45);
        tv.setLayoutParams(lp);

        tv.setSelected(expressCompany.isSelect());

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(expressCompany);
                }
            }
        });
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(ExpressCompany expressCompany);
    }

}
