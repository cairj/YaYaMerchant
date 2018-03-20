package com.yaya.merchant.activity;

import android.os.Handler;

import com.yaya.merchant.R;
import com.yaya.merchant.activity.login.LoginActivity;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.util.sp.SPUtil;
import com.yaya.merchant.util.sp.SpKeys;

/**
 * Created by admin on 2018/3/21.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        super.initData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SPUtil.getBoolean(SpKeys.IS_LOGIN)) {
                    openActivity(MainActivity.class, true);
                } else {
                    openActivity(LoginActivity.class, true);
                }
            }
        }, 3000);
    }
}
