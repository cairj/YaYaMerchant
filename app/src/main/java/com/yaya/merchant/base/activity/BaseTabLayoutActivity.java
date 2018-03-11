package com.yaya.merchant.base.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yaya.merchant.R;
import com.yaya.merchant.widgets.adapter.FragmentViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 蔡蓉婕 on 2018/3/9.
 */

public class BaseTabLayoutActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    protected TabLayout tabLayout;
    @BindView(R.id.view_pager)
    protected ViewPager viewPager;

    protected FragmentViewPagerAdapter adapter;
    protected List<Fragment> fragmentList = new ArrayList<>();

    protected List<String> tabTitleList = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        adapter = new FragmentViewPagerAdapter(fragmentManager, fragmentList, tabTitleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                onViewPagerScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                onViewPagerSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                onViewPagerScrollStateChanged(state);
            }
        });
    }

    protected void onViewPagerScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    protected void onViewPagerSelected(int position) {
    }

    protected void onViewPagerScrollStateChanged(int state) {
    }

}
