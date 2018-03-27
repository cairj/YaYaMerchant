package com.yaya.merchant.widgets.popupwindow;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yaya.merchant.R;
import com.yaya.merchant.base.BasePopupWindow;
import com.yaya.merchant.util.DpPxUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

/**
 * 筛选
 */

public class ScreenPopupWindow extends BasePopupWindow {

    private RecyclerView choiceItemListRv;
    protected BaseQuickAdapter mAdapter;

    public ScreenPopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.window_screen;
    }

    @Override
    protected void initView() {
        choiceItemListRv = windowView.findViewById(R.id.rv_choose_items);
        choiceItemListRv.setLayoutManager(new LinearLayoutManager(context));
        choiceItemListRv.addItemDecoration(addItemDecoration());
        choiceItemListRv.setAdapter(mAdapter);
    }

    public  <T> void setAdapter(BaseQuickAdapter<T> adapter) {
        mAdapter = adapter;
        choiceItemListRv.setAdapter(mAdapter);
    }

    protected RecyclerView.ItemDecoration addItemDecoration(){
        RecyclerView.ItemDecoration decoration = new VerticalDividerItemDecoration.Builder(context)
                .color(ContextCompat.getColor(context, R.color.transparent))
                .size(DpPxUtil.dp2px(11))
                .marginResId(R.dimen.margin_edge)
                .build();
        return decoration;
    }

}
