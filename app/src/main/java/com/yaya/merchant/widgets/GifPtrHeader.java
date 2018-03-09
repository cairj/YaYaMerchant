package com.yaya.merchant.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yaya.merchant.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by 魏新智 on 2017/3/27.
 */
public class GifPtrHeader extends FrameLayout implements PtrUIHandler {

    private ImageView gifImg;

    public GifPtrHeader(Context context) {
        super(context);
        initViews(null);
    }

    public GifPtrHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public GifPtrHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(attrs);
    }

    protected void initViews(AttributeSet attrs) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.header_gif_ptr_loading, this);
        gifImg = (ImageView) header.findViewById(R.id.gif_img);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        /*ImageLoaderHelper.loadResImg(R.drawable.ptr_loading, gifImg);*/
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
