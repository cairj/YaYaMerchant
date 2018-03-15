package com.yaya.merchant.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yaya.merchant.R;
import com.yaya.merchant.util.AppManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2018/2/23.
 */

public class BaseActivity extends AppCompatActivity {

    protected TextView backTv;
    protected TextView titleTv;
    protected TextView rightTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        beforeSetContent();
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();

        backTv = findViewById(R.id.tv_action_back);
        titleTv = findViewById(R.id.tv_title);
        rightTv = findViewById(R.id.tv_right);
        initActionBar();

        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }

    protected int getContentViewId() {
        return 0;
    }

    protected void initView() {
    }

    protected void initData() {
    }

    protected void beforeSetContent() {
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

    protected Fragment currentFragment;//当前显示的fragment
    protected FragmentManager fragmentManager;

    protected void showFragment(Fragment fragment) {
        if (fragment == currentFragment) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentFragment != null && currentFragment.isAdded()) {
            transaction.hide(currentFragment);
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(getFragmentContainerId(), fragment);
        }
        transaction.commitAllowingStateLoss();
        currentFragment = fragment;
    }

    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    protected void setActionBarTitle(String title) {
        if (titleTv != null) {
            titleTv.setText(title);
        }
    }

    protected void setActionBarRight(String right) {
        if (rightTv != null) {
            rightTv.setText(right);
        }
    }

    protected void initActionBar(){
        if (backTv != null){
            backTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backClick();
                }
            });
        }

        if (rightTv != null){
            rightTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rightClick();
                }
            });
        }
    }

    protected void backClick(){
        finish();
    }
    protected void rightClick(){
    }
}