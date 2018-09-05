package com.yaya.merchant.activity.withdraw;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.WithDrawMoneyAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.withdraw.BankCard;
import com.yaya.merchant.data.withdraw.MemberBalance;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.EventBusTags;
import com.yaya.merchant.util.LoadingUtil;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.util.ToastUtil;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by admin on 2018/3/15.
 */

public class WithdrawMoneyActivity extends BaseActivity {

    private String amount = "";
    private String bankAccountId = "";

    @BindView(R.id.tv_bank_card)
    protected TextView bankCardTv;
    @BindView(R.id.ed_money_count)
    protected EditText moneyCountEdit;
    @BindView(R.id.tv_total_money)
    protected TextView totalMoneyTv;
    @BindView(R.id.tv_member_balance)
    protected TextView memberBalanceTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_withdraw_money;
    }

    @Override
    protected void initData() {
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        setActionBarTitle("提现");
        setActionBarRight("提现记录");

        getBankCardData();
    }

    private void getBankCardData() {
        WithDrawMoneyAction.getBankCard(new GsonCallback<BankCard>(BankCard.class) {
            @Override
            public void onSucceed(JsonResponse<BankCard> response) {
                BankCard bankCard = response.getResultData();
                totalMoneyTv.setText(String.format(getString(R.string.withdraw_money_count), String.valueOf(bankCard.getCanAccountProceeds())));
                memberBalanceTv.setText("￥" + bankCard.getAccountBalance());
                amount = String.valueOf(bankCard.getCanAccountProceeds());

                if (bankCard.getBankAccount() != null) {
                    bankCardTv.setText(bankCard.getBankAccount().getBankName());
                    bankAccountId = String.valueOf(bankCard.getBankAccount().getId());
                }
            }
        });
    }

    @OnClick({R.id.tv_withdraw_all, R.id.tv_submit_withdraw, R.id.ll_bank_card})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_withdraw_all:
                moneyCountEdit.setText(amount);
                break;
            case R.id.tv_submit_withdraw:
                submitWithDrawMoney();
                break;
            case R.id.ll_bank_card:
                openActivity(ChangeDefaultBankCardActivity.class);
                break;
        }
    }

    private void submitWithDrawMoney() {
        WithDrawMoneyAction.getWithDrawMoney(moneyCountEdit.getText().toString().trim(), bankAccountId,
                new GsonCallback<String>(String.class) {
                    @Override
                    public void onSucceed(JsonResponse<String> response) {
                        ToastUtil.toast("提现成功");
                        getBankCardData();
                    }

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        LoadingUtil.showAsyncProgressDialog(WithdrawMoneyActivity.this, "提现中");
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        LoadingUtil.hideProcessingIndicator();
                    }
                });
    }

    @Override
    protected void rightClick() {
        openActivity(WithdrawMoneyRecordActivity.class);
    }

    @Subscriber(tag = EventBusTags.REFRESH_DEFAULT_BANK)
    private void refreshDefaultBank(String str) {
        getBankCardData();
    }
}
