package com.yaya.merchant.activity.search;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.interfaces.OnEditTextChangeListener;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/17.
 */

public abstract class BaseSearchActivity<T extends Serializable> extends BasePtrRecycleActivity<T> {

    protected String searchKey;
    protected InputMethodManager imm;

    @BindView(R.id.ll_screen)
    protected LinearLayout screenLL;
    @BindView(R.id.search_ed_input)
    protected EditText inputSearchEt;
    @BindView(R.id.fl_status)
    protected FrameLayout statusFl;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search_member;
    }

    @Override
    protected void initView() {
        super.initView();
        screenLL.setVisibility(View.GONE);
        initEditText();
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /*初始化输入框*/
    private void initEditText(){
        inputSearchEt.clearFocus();
        inputSearchEt.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchKey = charSequence.toString();
                refresh();
            }
        });
    }

    @OnClick({R.id.search_tv_cancel})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_tv_cancel:
                imm.hideSoftInputFromWindow(inputSearchEt.getWindowToken(), 0);
                if (!TextUtils.isEmpty(inputSearchEt.getText().toString())) {
                    inputSearchEt.setText("");
                } else {
                    finish();
                }
                break;
        }
    }

}
