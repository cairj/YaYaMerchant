package com.yaya.merchant.fragment.main;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainAction;
import com.yaya.merchant.activity.user.BankCardActivity;
import com.yaya.merchant.activity.user.EmployeeManagerActivity;
import com.yaya.merchant.activity.user.FeedBackActivity;
import com.yaya.merchant.activity.user.MerchantManagerActivity;
import com.yaya.merchant.activity.user.UserInfoActivity;
import com.yaya.merchant.activity.user.VerificationActivity;
import com.yaya.merchant.activity.user.VoiceSettingActivity;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.data.main.UserData;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;
import com.yaya.merchant.widgets.adapter.EmployeeManagerAdapter;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.user_tv_version)
    protected TextView versionTv;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initData() {
        initVersion();
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

    private void initVersion(){
        PackageInfo info = null;
        try {
            info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;
        versionTv.setText(version);
    }

    @OnClick({R.id.user_rl_merchant_manager,R.id.user_rl_employee_manager,R.id.user_rl_merchant_qrcode,
            R.id.user_rl_bank_card,R.id.user_rl_merchant_info,R.id.user_rl_set_voice,R.id.user_rl_verification,
            R.id.user_rl_feed_back})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.user_rl_merchant_manager:
                MerchantManagerActivity.open(getActivity(),MerchantManagerActivity.MERCHANT_MANAGER);
                break;
            case R.id.user_rl_employee_manager:
                openActivity(EmployeeManagerActivity.class);
                break;
            case R.id.user_rl_merchant_qrcode:
                MerchantManagerActivity.open(getActivity(),MerchantManagerActivity.MERCHANT_QR_CODE);
                break;
            case R.id.user_rl_bank_card:
                openActivity(BankCardActivity.class);
                break;
            case R.id.user_rl_merchant_info:
                openActivity(UserInfoActivity.class);
                break;
            case R.id.user_rl_set_voice:
                openActivity(VoiceSettingActivity.class);
                break;
            case R.id.user_rl_verification:
                new IntentIntegrator(getActivity())
                        .setOrientationLocked(false)
                        .setPrompt("将提货二维码放入框内即可自动扫描")
                        .setCaptureActivity(VerificationActivity.class) // 设置自定义的activity是VerificationActivity
                        .initiateScan(); // 初始化扫描
                break;
            case  R.id.user_rl_feed_back:
                openActivity(FeedBackActivity.class);
                break;
        }
    }

}
