package com.yaya.merchant.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by admin on 2018/2/23.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initView();
        initData();
    }

    protected int getContentViewId() {
        return 0;
    }

    protected void initView() {
    }

    protected void initData() {
    }

    protected void beforeSetContent(){
    }

    protected void openActivity(Class classes) {
        openActivity(classes, false);
    }

    protected void openActivity(Class classes, boolean isFinish) {
        Intent intent = new Intent(this, classes);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }

}
