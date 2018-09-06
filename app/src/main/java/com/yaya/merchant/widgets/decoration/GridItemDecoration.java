package com.yaya.merchant.widgets.decoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 蔡蓉婕 on 2018/9/6.
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpaceHorizontal;//列之间宽度
    private int mSpaceVertical;//行之间高度

    private int mColumnCount;
    private int mRowCount;

    public GridItemDecoration(int spaceHorizontal, int spaceVertical){
        mSpaceHorizontal = spaceHorizontal;
        mSpaceVertical = spaceVertical;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            mColumnCount = ((GridLayoutManager) layoutManager).getSpanCount();//几列
            int childCount = parent.getAdapter().getItemCount();
            if (childCount % mColumnCount > 0) {
                mRowCount = childCount / mColumnCount + 1;
            } else {
                mRowCount = childCount / mColumnCount;
            }

            int pos = parent.getChildAdapterPosition(view);
            //如果是在最右一列，则right为0,left为分割线一半
            if ((pos + 1) % mColumnCount == 0) {
                outRect.right = 0;
                outRect.left = mSpaceHorizontal/2;
            } else if((pos + 1) % mColumnCount == 1){//如果是在第一列，左侧为0，右侧为分割线一半
                outRect.right = mSpaceHorizontal/2;
                outRect.left = 0;
            }else {//中间的item，左右offset各为分割线一半
                outRect.left = mSpaceHorizontal/2;
                outRect.right = mSpaceHorizontal/2;
            }
            //如果是在最后一行，则bottom为0,否则为mSpaceVertical
            if ((pos + 1) > mColumnCount * (mRowCount - 1)) {
                outRect.bottom = 0;
            } else {
                outRect.bottom = mSpaceVertical;
            }
        }
    }
}
