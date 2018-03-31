package com.yaya.merchant.fragment.account;

import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.data.account.MemberData;
import com.yaya.merchant.net.callback.GsonCallback;

import butterknife.BindView;

/**
 * Created by admin on 2018/3/31.
 */

public class MemberDataFragment extends BaseFragment {

    @BindView(R.id.tv_today_recharge)
    protected TextView todayRechargeTv;
    @BindView(R.id.tv_online)
    protected TextView onlineTv;
    @BindView(R.id.tv_return)
    protected TextView returnTv;
    @BindView(R.id.tv_cash)
    protected TextView cashTv;
    @BindView(R.id.tv_today_consume)
    protected TextView todayConsumeTv;
    @BindView(R.id.tv_today_activation_member)
    protected TextView todayActivationMemberTv;
    @BindView(R.id.tv_total_recharge)
    protected TextView totalRechargeTv;
    @BindView(R.id.tv_total_recharge_balance)
    protected TextView totalRechargeBalanceTv;
    @BindView(R.id.tv_total_consume)
    protected TextView totalConsumeTv;
    @BindView(R.id.tv_total_member)
    protected TextView totalMemberTv;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_member_data;
    }

    @Override
    protected void initData() {
        super.initData();
        MainDataAction.getMemberData(new GsonCallback<MemberData>(MemberData.class) {
            @Override
            public void onSucceed(JsonResponse<MemberData> response) {
                MemberData data = response.getData().getData();
                todayRechargeTv.setText(data.getTodayRecharge());
                onlineTv.setText(data.getOnlineRecharge());
                returnTv.setText(data.getRebirth());
                cashTv.setText(data.getCashRecharge());
                todayConsumeTv.setText(data.getTodayPrice());
                todayActivationMemberTv.setText(data.getTodayMember());
                totalRechargeTv.setText(data.getTotalRecharge());
                totalRechargeBalanceTv.setText(data.getRechargeBalance());
                totalConsumeTv.setText(data.getTotalConsumption());
                totalMemberTv.setText(data.getTotalMember());
            }
        });
    }

    @Override
    public String getTitle() {
        return "数据";
    }
}
