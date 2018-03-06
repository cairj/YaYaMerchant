package com.yaya.merchant.activity;

import android.widget.TextView;

import com.yaya.merchant.R;
import com.yaya.merchant.base.BaseActivity;

import butterknife.BindViews;

/**
 * Created by admin on 2018/2/23.
 */

public class MainActivity extends BaseActivity {

    public static final int TAB_HOME = 0;
    public static final int TAB_ACTIVITY = 1;
    public static final int TAB_USER = 2;

//    protected

    @BindViews({R.id.main_tv_tab_home, R.id.main_tv_tab_activity, R.id.main_tv_tab_user})
    protected TextView[] tabTvs;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    protected void selectedTab(int position) {
        for (int i = 0; i < tabTvs.length; i++) {
            tabTvs[i].setSelected(false);
        }
        tabTvs[position].setSelected(true);
        switch (position) {

        }
    }



}
