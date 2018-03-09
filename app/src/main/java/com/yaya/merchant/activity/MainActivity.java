package com.yaya.merchant.activity;

import android.view.View;
import android.widget.TextView;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.fragment.main.ActivityFragment;
import com.yaya.merchant.fragment.main.HomeFragment;
import com.yaya.merchant.fragment.main.UserFragment;

import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by admin on 2018/2/23.
 */

public class MainActivity extends BaseActivity {

    public static final int TAB_HOME = 0;
    public static final int TAB_ACTIVITY = 1;
    public static final int TAB_USER = 2;

    protected HomeFragment homeFragment;
    protected ActivityFragment activityFragment;
    protected UserFragment userFragment;

    @BindViews({R.id.main_tv_tab_home, R.id.main_tv_tab_activity, R.id.main_tv_tab_user})
    protected TextView[] tabTvs;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        selectedTab(TAB_HOME);
    }

    protected void selectedTab(int position) {
        for (int i = 0; i < tabTvs.length; i++) {
            tabTvs[i].setSelected(false);
        }
        tabTvs[position].setSelected(true);
        switch (position) {
            case TAB_HOME:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                showFragment(homeFragment);
                break;
            case TAB_ACTIVITY:
                if (activityFragment == null) {
                    activityFragment = new ActivityFragment();
                }
                showFragment(activityFragment);
                break;
            case TAB_USER:
                if (userFragment == null) {
                    userFragment = new UserFragment();
                }
                showFragment(userFragment);
                break;
        }
    }


    @OnClick({R.id.main_tv_tab_home,R.id.main_tv_tab_activity,R.id.main_tv_tab_user})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.main_tv_tab_home:
                selectedTab(TAB_HOME);
                break;
            case R.id.main_tv_tab_activity:
                selectedTab(TAB_ACTIVITY);
                break;
            case R.id.main_tv_tab_user:
                selectedTab(TAB_USER);
                break;
        }
    }

}
