package com.yaya.merchant.fragment.main;

import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainAction;
import com.yaya.merchant.activity.account.BalanceAccountActivity;
import com.yaya.merchant.activity.account.EnterBillActivity;
import com.yaya.merchant.activity.account.MemberManagerActivity;
import com.yaya.merchant.activity.user.VerificationActivity;
import com.yaya.merchant.activity.withdraw.WithdrawMoneyActivity;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.data.main.HomeData;
import com.yaya.merchant.net.callback.GsonCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/6.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.home_tv_amount)
    protected TextView amountTv;
    @BindView(R.id.tv_today_transaction_number)
    protected TextView todayTransactionNumberTv;
    @BindView(R.id.tv_today_visitor_number)
    protected TextView todayVisitorNumberTv;
    @BindView(R.id.tv_today_new_member_number)
    protected TextView todayNewMemberNumberTv;
    @BindView(R.id.tv_total_transaction_number)
    protected TextView totalTransactionNumberTv;
    @BindView(R.id.tv_total_member_number)
    protected TextView totalMemberNumberTv;
    @BindView(R.id.tv_total_order_number)
    protected TextView totalOrderNumberTv;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        MainAction.getHomeData(new GsonCallback<HomeData>(HomeData.class) {
            @Override
            public void onSucceed(JsonResponse<HomeData> response) {
                amountTv.setText(response.getData().getData().getCollectionAmount());
                todayTransactionNumberTv.setText(response.getData().getData().getOrderNumber());
                todayVisitorNumberTv.setText(response.getData().getData().getVisitorCount());
                todayNewMemberNumberTv.setText(response.getData().getData().getAddMemberCount());
                totalTransactionNumberTv.setText(response.getData().getData().getOrderPriceTotal());
                totalMemberNumberTv.setText(response.getData().getData().getMemberTotal());
                totalOrderNumberTv.setText(response.getData().getData().getOrderPriceCount());
            }
        });
    }

    @OnClick({R.id.home_tv_amount,R.id.ll_member,R.id.tv_cash,R.id.tv_receivables,R.id.tv_balance_account})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.home_tv_amount:
                openActivity(EnterBillActivity.class);
                break;
            case R.id.ll_member:
                openActivity(MemberManagerActivity.class);
                break;
            case R.id.tv_cash:
                openActivity(WithdrawMoneyActivity.class);
                break;
            case R.id.tv_receivables:
                new IntentIntegrator(getActivity())
                        .setOrientationLocked(false)
                        .setPrompt("将提货二维码放入框内即可自动扫描")
                        .setCaptureActivity(VerificationActivity.class) // 设置自定义的activity是VerificationActivity
                        .initiateScan(); // 初始化扫描
                break;
            case R.id.tv_balance_account:
                openActivity(BalanceAccountActivity.class);
                break;
        }
    }

}
