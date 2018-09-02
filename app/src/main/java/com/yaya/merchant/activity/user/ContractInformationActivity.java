package com.yaya.merchant.activity.user;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.user.ContractInformation;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.UserHelper;

import butterknife.BindView;

/**
 */

public class ContractInformationActivity extends BaseActivity {

    @BindView(R.id.fl_shop_offline_rate)
    protected FrameLayout offlineRateFl;
    @BindView(R.id.fl_platform_rate)
    protected FrameLayout platformRateFl;
    @BindView(R.id.fl_commission_rate)
    protected FrameLayout commissionRateFl;
    @BindView(R.id.tv_shop_offline_rate)
    protected TextView offlineRateTv;
    @BindView(R.id.tv_platform_rate)
    protected TextView platformRateTv;
    @BindView(R.id.tv_commission_rate)
    protected TextView commissionRateTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_contract_information;
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("签约信息");
        if (UserHelper.isMerchant()){
            commissionRateFl.setVisibility(View.GONE);
        }
        if (UserHelper.isAgent()){
            offlineRateFl.setVisibility(View.GONE);
            platformRateFl.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        UserAction.getContractInformation(new GsonCallback<ContractInformation>(ContractInformation.class) {
            @Override
            public void onSucceed(JsonResponse<ContractInformation> response) {
                ContractInformation information = response.getResultData();
                if (UserHelper.isMerchant()) {
                    offlineRateTv.setText(information.getShopOfflineCommissionRate());
                    platformRateTv.setText(information.getShopPlatformCommissionRate());
                }
                if(UserHelper.isAgent()){
                    commissionRateTv.setText(information.getShopPlatformCommissionRate());
                }
            }
        });
    }
}
