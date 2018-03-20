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

public class DoubleBtnDialog extends BaseHintDialog {

    @BindView(R.id.tv_left_btn)
    protected TextView leftBtnTv;
    @BindView(R.id.tv_right_btn)
    protected TextView rightBtnTv;

    public DoubleBtnDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.dialog_double_button;
    }

    @OnClick({R.id.tv_left_btn,R.id.tv_right_btn})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.tv_left_btn:
                listener.leftClick();
                break;
            case R.id.tv_right_btn:
                listener.rightClick();
                break;
        }
    }

    public TextView getLeftBtnTv() {
        return leftBtnTv;
    }

    public TextView getRightBtnTv() {
        return rightBtnTv;
    }

    private OnClickListener listener;

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener{
        void leftClick();
        void rightClick();
    }

}
