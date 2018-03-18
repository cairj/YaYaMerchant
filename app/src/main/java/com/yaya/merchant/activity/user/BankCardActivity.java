package com.yaya.merchant.activity.user;

import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.user.BindInfo;
import com.yaya.merchant.net.callback.GsonCallback;

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
    @BindView(R.id.tv_legal_person_id_card)
    protected TextView legalPersonIdCardTv;

    @BindView(R.id.tv_bank_name)
    protected TextView bankNameTv;
    @BindView(R.id.tv_card_number)
    protected TextView bankCardNumberTv;
    @BindView(R.id.tv_card_type)
    protected TextView cardTypeTv;

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
                BindInfo bindInfo = response.getData().getData();
                companyNameTv.setText("公司名称："+bindInfo.getUserInfo().getName());
                businessLicenceTv.setText("营业执照："+bindInfo.getUserInfo().getBusinessLicenseNo());
                legalPersonTv.setText("企业法人："+bindInfo.getUserInfo().getCorporation());
                legalPersonIdCardTv.setText("法人身份证号："+bindInfo.getUserInfo().getCardId());

                bankNameTv.setText(bindInfo.getBankInfo().getBankName());
                bankCardNumberTv.setText(bindInfo.getBankInfo().getCardNo());
                bankNameTv.setText("储蓄卡");
            }
        });
    }
}
