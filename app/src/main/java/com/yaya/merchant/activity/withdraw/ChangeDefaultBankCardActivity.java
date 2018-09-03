package com.yaya.merchant.activity.withdraw;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.WithDrawMoneyAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.withdraw.BankCard;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.util.EventBusTags;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.widgets.adapter.BankCardAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 蔡蓉婕 on 2018/9/3.
 */

public class ChangeDefaultBankCardActivity extends BaseActivity {

    @BindView(R.id.rv_bank_list)
    protected RecyclerView bankListRv;

    BankCardAdapter adapter;
    ArrayList<BankCard.BankAccount> bankList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_change_default_bank_card;
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("提现");

        bankListRv.setLayoutManager(new LinearLayoutManager(this));
        bankListRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).size(DpPxUtil.dp2px(30)).color(Color.TRANSPARENT).build());
        adapter = new BankCardAdapter(bankList);
        adapter.setListener(new BankCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BankCard.BankAccount bankAccount) {
                WithDrawMoneyAction.changeDefaultBankCard(String.valueOf(bankAccount.getId()), new GsonCallback<String>(String.class) {
                    @Override
                    public void onSucceed(JsonResponse<String> response) {
                        ToastUtil.toast("修改成功");
                        EventBus.getDefault().post("", EventBusTags.REFRESH_DEFAULT_BANK);
                        finish();
                    }
                });
            }
        });
        bankListRv.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        WithDrawMoneyAction.getBankAccountList(new GsonCallback<BankCard.BankAccount>(BankCard.BankAccount.class) {
            @Override
            public void onSucceed(JsonResponse<BankCard.BankAccount> response) {
                bankList.clear();
                bankList.addAll(response.getDataList());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
