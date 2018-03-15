package com.yaya.merchant.fragment.main;

import android.widget.ImageView;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainAction;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.data.main.UserData;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import butterknife.BindView;

/**
 * Created by admin on 2018/3/7.
 */

public class UserFragment extends BaseFragment {

    @BindView(R.id.user_iv_head)
    protected ImageView headIv;
    @BindView(R.id.user_tv_name)
    protected TextView nameTv;
    @BindView(R.id.user_tv_role_name)
    protected TextView roleNameTv;
    @BindView(R.id.user_tv_merchant_manager)
    protected TextView merchantManagerTv;
    @BindView(R.id.user_tv_employee_manager)
    protected TextView employeeManagerTv;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initData() {
        MainAction.getUserData(new GsonCallback<UserData>(UserData.class) {
            @Override
            public void onSucceed(JsonResponse<UserData> response) {
                UserData userData = response.getData().getData();
                GlideLoaderHelper.loadAvatar(userData.getHeadImgUrl(),headIv);
                nameTv.setText(userData.getName());
                roleNameTv.setText(userData.getRoleName());
                merchantManagerTv.setText(userData.getStoreCount());
                employeeManagerTv.setText(userData.getUserCount());
            }
        });
    }
}
