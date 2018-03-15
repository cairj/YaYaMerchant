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
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/15.
 */

public class WithdrawMoneyActivity extends BaseActivity {

    private String amount = "";

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
        getMemberBalance();
    }

    private void getBankCardData() {
        WithDrawMoneyAction.getBankCard(new GsonCallback<BankCard>(BankCard.class) {
            @Override
            public void onSucceed(JsonResponse<BankCard> response) {
                String text = response.getData().getData().getBankName();
                String bankCard = response.getData().getData().getCardNo();
                bankCard = bankCard.substring(bankCard.length() - 4);
                bankCardTv.setText(text + "(" + bankCard + ")");
            }
        });
    }

    private void getMemberBalance() {
        WithDrawMoneyAction.getMemberBalance(new GsonCallback<MemberBalance>(MemberBalance.class) {
            @Override
            public void onSucceed(JsonResponse<MemberBalance> response) {
                MemberBalance memberBalance = response.getData().getData();
                totalMoneyTv.setText(String.format(getString(R.string.withdraw_money_count), memberBalance.getAmount()));
                memberBalanceTv.setText("￥" + memberBalance.getBalance());
                amount = memberBalance.getAmount();
            }
        });
    }

    @OnClick({R.id.tv_withdraw_all, R.id.tv_submit_withdraw})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_withdraw_all:
                moneyCountEdit.setText(amount);
                break;
            case R.id.tv_submit_withdraw:
                submitWithDrawMoney();
                break;
        }
    }

    private void submitWithDrawMoney() {
        WithDrawMoneyAction.getWithDrawMoney(moneyCountEdit.getText().toString().trim(),
                new GsonCallback<String>(String.class) {
                    @Override
                    public void onSucceed(JsonResponse<String> response) {
                        ToastUtil.toast(response.getData().getData());
                    }
                });
    }

    @Override
    protected void rightClick() {
        openActivity(WithdrawMoneyRecordActivity.class);
    }
}
