package com.yaya.merchant.widgets.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.yaya.merchant.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/21.
 */

public class SingleBtnDialog extends BaseHintDialog {

    @BindView(R.id.tv_submit)
    protected TextView submitBtnTv;

    public SingleBtnDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.dialog_single_button;
    }

    @OnClick({R.id.tv_submit})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                listener.submit();
                break;
        }
    }

    public TextView getSubmitBtnTv() {
        return submitBtnTv;
    }

    private OnClickListener listener;

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void submit();
    }

}
