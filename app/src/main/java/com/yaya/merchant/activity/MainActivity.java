package com.yaya.merchant.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.fragment.goods.GoodsFragment;
import com.yaya.merchant.fragment.main.OrderFragment;
import com.yaya.merchant.fragment.main.HomeFragment;
import com.yaya.merchant.fragment.main.UserFragment;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.service.GetOrderService;
import com.yaya.merchant.util.BadgeUtil;
import com.yaya.merchant.util.SystemUtil;

import butterknife.BindView;
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
    protected GoodsFragment goodsFragment;
    protected UserFragment userFragment;

    @BindViews({R.id.main_tv_tab_home, R.id.main_tv_tab_activity, R.id.main_tv_tab_user})
    protected TextView[] tabTvs;

    @BindView(R.id.tv_order_count)
    protected TextView orderCountTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        selectedTab(TAB_HOME);
        /*if (!SystemUtil.isServiceRunning(this, GetOrderService.class.getName())){
            Log.e("main","1111");
            Intent i = new Intent(this,GetOrderService.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startService(i);
        }*/
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
                if (goodsFragment == null) {
                    goodsFragment = new GoodsFragment();
                }
                showFragment(goodsFragment);
                break;
            case TAB_USER:
                if (userFragment == null) {
                    userFragment = new UserFragment();
                }
                showFragment(userFragment);
                break;
        }
    }


    @OnClick({R.id.main_tv_tab_home, R.id.main_rl_tab_activity, R.id.main_tv_tab_user})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_tv_tab_home:
                selectedTab(TAB_HOME);
                break;
            case R.id.main_rl_tab_activity:
                selectedTab(TAB_ACTIVITY);
                break;
            case R.id.main_tv_tab_user:
                selectedTab(TAB_USER);
                break;
        }
    }

    public void setOrderCount(String orderCount){
        if(Integer.parseInt(orderCount)>0) {
            orderCountTv.setText(orderCount);
            orderCountTv.setVisibility(View.VISIBLE);
        }else {
            orderCountTv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNewOrderCount();
    }

    private void getNewOrderCount(){
        MainAction.getOrderCount(new GsonCallback<String>(String.class) {
            @Override
            public void onSucceed(JsonResponse<String> response) {
                BadgeUtil.setBadgeCount(MainActivity.this,Integer.parseInt(response.getResultData()),R.color.red_ff6257);
            }
        });
    }

}
