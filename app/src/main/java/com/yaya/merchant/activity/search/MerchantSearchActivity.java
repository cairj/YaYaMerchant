package com.yaya.merchant.activity.search;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.activity.user.MerchantManagerActivity;
import com.yaya.merchant.activity.user.MerchantQRCodeActivity;
import com.yaya.merchant.data.account.Member;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.data.user.MerchantData;
import com.yaya.merchant.widgets.adapter.MemberManagerAdapter;
import com.yaya.merchant.widgets.adapter.MerchantManagerAdapter;

/**
 * Created by admin on 2018/3/17.
 */

public class MerchantSearchActivity extends BaseSearchActivity<MerchantData> {

    public static final int MERCHANT_MANAGER = 1;
    public static final int MERCHANT_QR_CODE = 2;//二维码

    private int type;

    public static void open(Context context, int type) {
        Intent intent = new Intent(context, MerchantSearchActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        MerchantManagerAdapter adapter= new MerchantManagerAdapter(mDataList);
        adapter.setListener(new MerchantManagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(MerchantData merchantData) {
                if (type == MERCHANT_QR_CODE) {
                    MerchantQRCodeActivity.open(MerchantSearchActivity.this, merchantData.getStoreId(),
                            merchantData.getName());
                }
            }
        });
        return adapter;
    }

    @Override
    protected JsonResponse<BaseRowData<MerchantData>> getData() throws Exception {
        return UserAction.getMerchantList("", searchKey,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", MERCHANT_MANAGER);
        super.initView();
        inputSearchEt.setHint("搜索门店");
        statusFl.setVisibility(View.GONE);
    }

}
