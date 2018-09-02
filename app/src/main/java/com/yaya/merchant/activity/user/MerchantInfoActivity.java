package com.yaya.merchant.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.user.MerchantInfo;
import com.yaya.merchant.net.callback.GsonCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/9/2.
 */

public class MerchantInfoActivity extends BaseActivity {

    @BindView(R.id.tv_merchant_name)
    protected TextView merchantNameTv;
    @BindView(R.id.tv_merchant_master)
    protected TextView merchantMasterTv;
    @BindView(R.id.tv_merchant_phone)
    protected TextView merchantPhoneTv;
    @BindView(R.id.tv_merchant_agent)
    protected TextView merchantAgentTv;
    @BindView(R.id.tv_merchant_member)
    protected TextView merchantMemberTv;
    @BindView(R.id.tv_merchant_license_number)
    protected TextView merchantLicenseNumberTv;
    @BindView(R.id.tv_offline_commission_rate)
    protected TextView offlineCommissionRateTv;
    @BindView(R.id.tv_platform_commission_rate)
    protected TextView platformCommissionRateTv;
    @BindView(R.id.tv_account_contact)
    protected TextView accountContactTv;
    @BindView(R.id.tv_merchant_bank)
    protected TextView merchantBankTv;
    @BindView(R.id.tv_merchant_bank_account)
    protected TextView bankAccountTv;
    @BindView(R.id.tv_merchant_bank_number)
    protected TextView merchantBankNumberTv;

    private String phone;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_merchant_info;
    }

    @Override
    protected void initData() {
        super.initData();
        UserAction.getMerchantInfo(new GsonCallback<MerchantInfo>(MerchantInfo.class) {
            @Override
            public void onSucceed(JsonResponse<MerchantInfo> response) {
                MerchantInfo info = response.getResultData();
                merchantNameTv.setText(info.getShopName());
                merchantMasterTv.setText(info.getCompanyFuze());
                merchantPhoneTv.setText(info.getCompanyPhone());
                merchantAgentTv.setText(info.getAgentName());
                merchantMemberTv.setText(info.getAccounts());
                merchantLicenseNumberTv.setText(info.getLicenseNumber());

                offlineCommissionRateTv.setText(info.getShopOfflineCommissionRate());
                platformCommissionRateTv.setText(info.getShopPlatformCCommissionRate());
                accountContactTv.setText(info.getAccountContact());
                merchantBankTv.setText(info.getBank());
                bankAccountTv.setText(info.getAccounts());
                merchantBankNumberTv.setText(info.getLicenseNumber());

                phone = info.getCompanyPhone();
            }
        });
    }

    @OnClick(R.id.fl_phone)
    protected void onClick(){
        if (TextUtils.isEmpty(phone)){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }
}
