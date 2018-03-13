package com.yaya.merchant.fragment.account;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.BalanceAccountAction;
import com.yaya.merchant.base.fragment.BasePtrRecycleFragment;
import com.yaya.merchant.data.account.BillData;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.widgets.adapter.MerchantBillGroupAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * 入账
 */

public class MerchantBillFragment extends BaseBillFragment {

    protected String payState = "";
    protected String payType = "";

    @Override
    protected JsonResponse<BaseRowData<BillData>> getData() throws Exception {
        return BalanceAccountAction.getAccountList(storeId, payState, payType, startTime, endTime,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void initSingleChoiceWindow() {
        super.initSingleChoiceWindow();

    }

    @Override
    public String getTitle() {
        return "入账";
    }
}
