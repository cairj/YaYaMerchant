package com.yaya.merchant.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by 蔡蓉婕 on 2018/3/6.
 */

public class BaseFragment extends Fragment {

    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentViewId(), null);
        ButterKnife.bind(this,rootView);
        initView();
        initData();
        return rootView;
    }

    protected int getContentViewId() {
        return 0;
    }

    protected void initView() {
    }

    protected void initData() {
    }

    public String getTitle(){
        return "";
    }
}
