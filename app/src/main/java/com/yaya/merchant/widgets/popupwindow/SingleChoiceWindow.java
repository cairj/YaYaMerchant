package com.yaya.merchant.widgets.popupwindow;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yaya.merchant.R;
import com.yaya.merchant.base.BasePopupWindow;
import com.yaya.merchant.util.DeviceParamsUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * 单选弹窗
 */

public class SingleChoiceWindow extends BasePopupWindow {

    protected RecyclerView choiceItemListRv;
    protected BaseQuickAdapter mAdapter;

    public SingleChoiceWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.window_recycler_view_list;
    }

    @Override
    protected void initView() {
        choiceItemListRv = windowView.findViewById(R.id.recycler_view);
        choiceItemListRv.setLayoutManager(new LinearLayoutManager(context));
        choiceItemListRv.addItemDecoration(addItemDecoration());
        choiceItemListRv.setAdapter(mAdapter);
    }

    public  <T> void setAdapter(BaseQuickAdapter<T> adapter) {
        mAdapter = adapter;
        choiceItemListRv.setAdapter(mAdapter);
    }

    protected RecyclerView.ItemDecoration addItemDecoration(){
        RecyclerView.ItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(context)
                .color(ContextCompat.getColor(context, R.color.gray_F6F7F9))
                .sizeResId(R.dimen.divider_height)
                .build();
        return decoration;
    }

    @Override
    public void showDropDown(View view) {
        //int height = DeviceParamsUtil.getScreenWidth(context)/2;
        //setHeight();
        super.showDropDown(view);
    }
}
