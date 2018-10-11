package com.yaya.merchant.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yaya.merchant.R;
import com.yaya.merchant.util.DeviceParamsUtil;

/**
 * 下拉弹窗基准类
 */
public abstract class BasePopupWindow extends PopupWindow {

    protected Context context;
    protected LayoutInflater inflater;

    protected View mExternalView;

    protected View windowView;

    public BasePopupWindow(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        windowView = inflater.inflate(getLayoutResId(), null);
        this.setContentView(windowView);

        initLayoutParams();

        setFocusable(true);
        //刷新状态
        update();
        //点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);

        setAnimationStyle(0);

        initComponent();
        initView();
        initData();
        initListener();
        setExternalViewCanDismiss();
    }

    protected BasePopupWindow() {
    }

    protected abstract int getLayoutResId();

    protected void initComponent() {
    }

    protected void initView() {
    }

    protected void initData() {
    }

    protected void initListener() {
    }

    protected void initLayoutParams() {
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void show() {
        show(((Activity) context).getWindow().getDecorView());
    }

    public void show(View view) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();
        }
    }

    public void showDropDown(View view) {
        showDropDown(view, 0, 0);
    }

    public void showDropDown(View view, int x, int y) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < 24) {
                this.showAsDropDown(view, x, y);
            } else {
                this.showAtLocation(view, Gravity.NO_GRAVITY, x, view.getHeight() + DeviceParamsUtil.getStatusBarHeight(view.getContext())+y);
            }
        } else {
            this.dismiss();
        }
    }

    protected void setExternalViewCanDismiss(final View externalView) {
        externalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setExternalViewCanDismiss() {
        mExternalView = windowView.findViewById(R.id.external_view);
        if (mExternalView != null) {
            mExternalView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
    }

    private void setAlpha() {

    }
}
