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

    private int contentViewLayoutId = R.layout.dialog_single_button;

    @BindView(R.id.tv_submit)
    protected TextView submitBtnTv;

    public SingleBtnDialog(@NonNull Context context) {
        super(context);
    }

    public SingleBtnDialog(@NonNull Context context,int contentViewLayoutId) {
        super(context,contentViewLayoutId);
    }

    @Override
    protected int getContentViewId() {
        return contentViewLayoutId;
    }

    @OnClick({R.id.tv_submit})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (listener != null) {
                    listener.submit();
                } else {
                    dismiss();
                }
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
