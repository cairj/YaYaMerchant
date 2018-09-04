package com.yaya.merchant.activity.account;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.account.BalanceAccount;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.widgets.popupwindow.screenwindow.TimePickerWindow;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/24.
 */

public class BalanceAccountActivity extends BaseActivity {

    public static int TO_MERCHANT_REQUEST_CODE = 10000;

    @BindView(R.id.tv_order_price)
    protected TextView orderPriceTv;
    @BindView(R.id.tv_fee_total)
    protected TextView feeTotalTv;
    @BindView(R.id.tv_refund_money_total)
    protected TextView refundMoneyTotalTv;
    @BindView(R.id.tv_real_amount)
    protected TextView realAmountTv;
    @BindView(R.id.tv_collection_amount)
    protected TextView collectionAmountTv;
    @BindView(R.id.tv_income_amount)
    protected TextView incomeAmountTv;
    @BindView(R.id.tv_order_total)
    protected TextView orderTotalTv;
    @BindView(R.id.tv_refund_count)
    protected TextView refundCountTv;
    @BindView(R.id.tv_choose_time)
    protected TextView chooseTimeTv;
    @BindView(R.id.fl_merchant)
    protected FrameLayout merchantFl;

    protected String storeId = "";
    protected String startTime = "";
    protected String endTime = "";

    private TimePickerWindow timePickerWindow;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_balance_account;
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        setActionBarTitle("对账");
        getAllMerchant();
        initTimePickerWindow();
    }

    private void initTimePickerWindow() {
        timePickerWindow = new TimePickerWindow(this);
        timePickerWindow.setListener(new TimePickerWindow.OnSubmitListener() {
            @Override
            public void submit(String startTime, String endTime, String phone) {
                BalanceAccountActivity.this.startTime = startTime;
                BalanceAccountActivity.this.endTime = endTime;
                getBalanceAccount();
                chooseTimeTv.setText(startTime+"\n"+endTime);
            }
        });
        timePickerWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                chooseTimeTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_down, 0);
            }
        });
    }

    private void getAllMerchant() {
        Type type = new TypeToken<BaseRowData<Merchant>>() {
        }.getType();
        MainDataAction.searchMerchant( new GsonCallback<BaseRowData<Merchant>>(type) {
            @Override
            public void onSucceed(JsonResponse<BaseRowData<Merchant>> response) {
                storeId = "";
                for (Merchant merchant : (response.getData().getData()).getRows()) {
                    storeId = (TextUtils.isEmpty(storeId) ? "" : storeId + ",") + merchant.getId();
                }
                getBalanceAccount();
            }
        });
    }

    private void getBalanceAccount() {
        MainDataAction.getBalanceAccount(storeId, startTime, endTime, new GsonCallback<BalanceAccount>(BalanceAccount.class) {
            @Override
            public void onSucceed(JsonResponse<BalanceAccount> response) {
                BalanceAccount data = response.getData().getData();
                orderPriceTv.setText("+" + data.getOrderPrice());
                feeTotalTv.setText("-" + data.getFeeTotal());
                refundMoneyTotalTv.setText("-" + data.getRefundMoneyTotal());
                realAmountTv.setText(data.getRealAmount());
                collectionAmountTv.setText(data.getCollectionAmount());
                incomeAmountTv.setText(data.getIncomeAmount());
                orderTotalTv.setText(data.getOrdertotal());
                refundCountTv.setText(data.getRefundCount());
            }
        });
    }

    @OnClick({R.id.fl_merchant, R.id.tv_day_bill, R.id.tv_week_bill, R.id.tv_month_bill,R.id.fl_choose_time})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_merchant:
                startActivityForResult(SearchMerchantActivity
                                .openSearchIntent(this, storeId),
                        TO_MERCHANT_REQUEST_CODE);
                break;
            case R.id.tv_day_bill:
                BillListActivity.open(this, BillListActivity.DAILY_BILL);
                break;
            case R.id.tv_week_bill:
                BillListActivity.open(this, BillListActivity.WEEK_BILL);
                break;
            case R.id.tv_month_bill:
                BillListActivity.open(this, BillListActivity.MONTH_BILL);
                break;
            case R.id.fl_choose_time:
                timePickerWindow.showDropDown(merchantFl);
                chooseTimeTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_up, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == TO_MERCHANT_REQUEST_CODE) {
            storeId = data.getStringExtra(SearchMerchantActivity.RETURN_SELECT_MERCHANT_ID);
            getBalanceAccount();
        }
    }

}
