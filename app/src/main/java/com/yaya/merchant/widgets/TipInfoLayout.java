package com.yaya.merchant.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yaya.merchant.util.GitViewUtils;


/**
 * 一些提示信息显示，包含有加载过程的显示
 *
 * Created by 火蚁 on 15/4/16.
 */
public class TipInfoLayout extends FrameLayout {

    private String netWorkError = "轻触重新加载";
    private String empty = "咦~这里空空如也~";

    private ProgressBar mPbProgressBar;

    private View mTipContainer;
    private FrameLayout netErrorContainer;//网络错误的视图容器
    private FrameLayout emptyViewContainer;//空数据的视图容器

    private TextView mTvTipState;

    private TextView mTvTipMsg;

    public TipInfoLayout(Context context) {
        super(context);
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.tip_info_layout, null, false);
        mPbProgressBar = GitViewUtils.findViewById(view, R.id.pb_loading);
        mTvTipState = GitViewUtils.findViewById(view, R.id.tv_tip_state);
        mTvTipMsg = GitViewUtils.findViewById(view, R.id.tv_tip_msg);
        mTipContainer = GitViewUtils.findViewById(view, R.id.ll_tip);

        netErrorContainer = GitViewUtils.findViewById(view, R.id.net_error_container);
        emptyViewContainer = GitViewUtils.findViewById(view, R.id.empty_view_container);
        setLoading();
        addView(view);
    }

    public void setOnClick(OnClickListener onClik) {
        this.setOnClickListener(onClik);
    }

    public void setHiden() {
        this.setVisibility(View.GONE);
    }

    public void setLoading() {
        this.setVisibility(VISIBLE);
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTipContainer.setVisibility(View.GONE);

        this.netErrorContainer.setVisibility(View.GONE);
        this.emptyViewContainer.setVisibility(View.GONE);
    }

    public void setLoadError() {
        setLoadError("");
    }

    public void setLoadError(String errorTip) {
        this.setVisibility(VISIBLE);
        String tip = netWorkError;
        if (errorTip != null && !TextUtils.isEmpty(errorTip))
            tip = errorTip;
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTipContainer.setVisibility(View.VISIBLE);
        this.mTvTipState.setText(R.string.fa_wifi);
        this.mTvTipMsg.setText(tip);
    }

    public void setEmptyData(String emptyTip) {
        this.setVisibility(VISIBLE);
        String tip = empty;
        if (emptyTip != null && !TextUtils.isEmpty(emptyTip))
            tip = emptyTip;
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTipContainer.setVisibility(View.VISIBLE);
        this.mTvTipState.setText(R.string.fa_refresh);
        this.mTvTipMsg.setText(tip);
    }

    //显示网络错误的视图
    public void showNetErrorContainer(){
        this.setVisibility(View.VISIBLE);
        this.netErrorContainer.setVisibility(View.VISIBLE);
        this.emptyViewContainer.setVisibility(View.GONE);
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTipContainer.setVisibility(View.GONE);
    }

    //显示空数据的视图
    public void showEmptyViewContainer(){
        this.setVisibility(View.VISIBLE);
        this.netErrorContainer.setVisibility(View.GONE);
        this.emptyViewContainer.setVisibility(View.VISIBLE);
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTipContainer.setVisibility(View.GONE);
    }

    //设置网络错误视图
    public void setNetErrorContainer(View view){
        netErrorContainer.removeAllViews();
        netErrorContainer.addView(view);
    }

    //设置空数据视图
    public void setEmptyViewContainer(View view){
        emptyViewContainer.removeAllViews();
        emptyViewContainer.addView(view);
    }

    //
    public View getEmptyViewContainer(){
        return emptyViewContainer;
    }
}
