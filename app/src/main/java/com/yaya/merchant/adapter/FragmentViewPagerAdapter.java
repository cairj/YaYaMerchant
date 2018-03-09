package com.yaya.merchant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yaya.merchant.base.fragment.BaseFragment;

import java.util.List;

/**
 * Created by 蔡蓉婕 on 2018/3/9.
 */

public class FragmentViewPagerAdapter  extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titleList;

    public FragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public FragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList != null) {
            return titleList.get(position);
        }
        Fragment fragment = fragmentList.get(position);
        if (fragment instanceof BaseFragment) {
            return ((BaseFragment) fragment).getTitle();
        }
        return fragment.getTag();
    }
}