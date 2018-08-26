package com.yaya.merchant.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaya.merchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2018/3/21.
 */

public abstract class BaseHintDialog extends Dialog {

    protected int width = -1;
    protected int height = -1;

    protected Context mContent;
    protected View rootView;

    //@BindView(R.id.iv_picture)
    protected ImageView pictureIv;
    //@BindView(R.id.tv_title)
    protected TextView titleTv;
    //@BindView(R.id.tv_content)
    protected TextView contentTv;

    public BaseHintDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        initView(context, getContentViewId());
    }

    public BaseHintDialog(@NonNull Context context, int contentViewLayoutId) {
        super(context, R.style.Dialog);
        initView(context, contentViewLayoutId);
    }

    private void initView(Context context, int contentViewLayoutId) {
        mContent = context;
        rootView = LayoutInflater.from(mContent).inflate(contentViewLayoutId, null);
        setContentView(rootView);
        ButterKnife.bind(this, rootView);

        pictureIv = rootView.findViewById(R.id.iv_picture);
        titleTv = rootView.findViewById(R.id.tv_title);
        contentTv = rootView.findViewById(R.id.tv_content);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置宽高
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (width != -1) {
            lp.width = width;
        }
        if (height != -1) {
            lp.height = height;
        }
        getWindow().setAttributes(lp);
    }

    public ImageView getPictureIv() {
        return pictureIv;
    }

    public TextView getTitleTv() {
        return titleTv;
    }

    public TextView getContentTv() {
        return contentTv;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    protected abstract int getContentViewId();

}
