package com.yaya.merchant.widgets.adapter;

import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.widgets.popupwindow.SingleChoiceWindow;

import java.util.List;

/**
 * Created by admin on 2018/4/1.
 */

public class ScreenWindowAdapter extends SingleChoiceTextAdapter {

    private int itemWidth = 106;

    public ScreenWindowAdapter(List<ChoiceItem> data, int itemWidth) {
        super(R.layout.textview_gray,data);
        this.itemWidth = itemWidth;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ChoiceItem item) {
        super.convert(baseViewHolder, item);
        TextView tv = baseViewHolder.getView(R.id.text_view);
        ViewGroup.LayoutParams lp = tv.getLayoutParams();
        lp.height = DpPxUtil.dp2px(29);
        lp.width = DpPxUtil.dp2px(itemWidth);
        tv.setLayoutParams(lp);
    }
}
