package com.yaya.merchant.activity.user;

import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.user.BindInfo;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.UserHelper;

import butterknife.BindView;

/**
 * Created by admin on 2018/3/17.
 */

public class BankCardActivity extends BaseActivity {

    @BindView(R.id.tv_company_name)
    protected TextView companyNameTv;
    @BindView(R.id.tv_business_licence)
    protected TextView businessLicenceTv;
    @BindView(R.id.tv_legal_person)
    protected TextView legalPersonTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bank_card;
    }

    @Override
    protected void initData() {
        super.initData();
        setActionBarTitle("认证信息");
        UserAction.getBindInfo(new GsonCallback<BindInfo>(BindInfo.class) {
            @Override
            public void onSucceed(JsonResponse<BindInfo> response) {
                BindInfo bindInfo = response.getResultData();
                companyNameTv.setText("公司名称："+bindInfo.getShopCompanyName());
                businessLicenceTv.setText("营业执照："+bindInfo.getLicenseNumber());

                if (UserHelper.isMerchant()) {
                    legalPersonTv.setText("企业法人:" + bindInfo.getCompanyFuze());
                }

                if (UserHelper.isAgent()) {
                    legalPersonTv.setText("企业法人:" + bindInfo.getCompanyFaren());
                }
            }
        });
    }
}
