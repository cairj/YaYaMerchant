package com.yaya.merchant.activity.user;

import android.view.View;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseActivity;

import butterknife.OnClick;

/**
 * Created by admin on 2018/3/18.
 */

public class UserInfoActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_info;
    }

    @OnClick({R.id.tv_information})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.tv_information:
                openActivity(InformationActivity.class);
                break;
        }
    }

}
