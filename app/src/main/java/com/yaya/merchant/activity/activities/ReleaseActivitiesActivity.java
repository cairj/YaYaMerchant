package com.yaya.merchant.activity.activities;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseWebViewActivity;
import com.yaya.merchant.net.UrlH5s;
import com.yaya.merchant.util.StatusBarUtil;

/**
 * Created by admin on 2018/5/20.
 */

public class ReleaseActivitiesActivity extends BaseWebViewActivity {

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        setActionBarTitle("发布活动");
    }

    @Override
    protected void initData() {
        url = UrlH5s.RELEASE_ACTIVITIES;
        super.initData();
    }

}
