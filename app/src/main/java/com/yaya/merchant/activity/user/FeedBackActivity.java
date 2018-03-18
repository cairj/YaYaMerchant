package com.yaya.merchant.activity.user;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.interfaces.OnEditTextChangeListener;

import butterknife.BindView;

/**
 * Created by admin on 2018/3/18.
 */

public class FeedBackActivity extends BaseActivity {

    private static final int MAX_CONTENT_SIZE = 100;

    @BindView(R.id.ed_input_feed_back)
    protected EditText feedBackEdit;
    @BindView(R.id.tv_input_size)
    protected TextView inputSizeTv;
    @BindView(R.id.rv_images)
    protected RecyclerView imagesRv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("意见反馈");
        setActionBarRight("提交");
        initInput();
    }

    private void initInput() {
        feedBackEdit.setMaxEms(MAX_CONTENT_SIZE);
        feedBackEdit.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputSizeTv.setText(String.format("%d/%d",
                        feedBackEdit.getText().toString().trim().length(), MAX_CONTENT_SIZE));
            }
        });
    }

}
