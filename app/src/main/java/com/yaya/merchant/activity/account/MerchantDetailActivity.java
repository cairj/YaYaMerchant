package com.yaya.merchant.activity.account;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.net.callback.GsonCallback;

import butterknife.BindView;

/**
 * Created by 蔡蓉婕 on 2018/9/18.
 */

public class MerchantDetailActivity extends BaseActivity {

    @BindView(R.id.tv_store_name)
    protected TextView storeNameTv;
    @BindView(R.id.tv_user_name)
    protected TextView userNameTv;
    @BindView(R.id.tv_contacts)
    protected TextView contactsTv;
    @BindView(R.id.tv_phone)
    protected TextView phoneTv;

    private String storeName;
    private String storeId;

    public static void open(Context context,String storeName,String storeId){
        Intent intent = new Intent(context,MerchantDetailActivity.class);
        intent.putExtra("storeName",storeName);
        intent.putExtra("storeId",storeId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_merchant_bill_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        storeName = getIntent().getStringExtra("storeName");
        storeId = getIntent().getStringExtra("storeName");
        setActionBarTitle("门店");
        MainDataAction.getMerchantInfo(storeId, new GsonCallback<Merchant>(Merchant.class) {
            @Override
            public void onSucceed(JsonResponse<Merchant> response) {
                storeNameTv.setText(storeName);
                userNameTv.setText(response.getResultData().getUserName());
                contactsTv.setText(response.getResultData().getContactsName());
                phoneTv.setText(response.getResultData().getPhone());
            }
        });
    }
}
