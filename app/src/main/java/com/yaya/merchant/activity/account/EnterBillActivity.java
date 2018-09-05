package com.yaya.merchant.activity.account;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseTabLayoutActivity;
import com.yaya.merchant.fragment.account.MemberBillFragment;
import com.yaya.merchant.fragment.account.MerchantBillFragment;
import com.yaya.merchant.util.Constants;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 入账列表
 */

public class EnterBillActivity extends BaseTabLayoutActivity {

    public static int TO_MERCHANT_REQUEST_CODE = 10000;

    private MerchantBillFragment merchantBillFragment;
    private MemberBillFragment memberBillFragment;

    @BindView(R.id.external_view)
    protected View externalView;
    @BindView(R.id.tv_search)
    protected EditText searchEd;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_enter_bill;
    }

    @Override
    protected void initView() {
        super.initView();
        initInputEd();
    }

    @Override
    protected void initData() {
        merchantBillFragment = new MerchantBillFragment();
        fragmentList.add(merchantBillFragment);

        memberBillFragment = new MemberBillFragment();
        fragmentList.add(memberBillFragment);

        adapter.notifyDataSetChanged();
    }

    private void initInputEd(){
        searchEd.setHint("搜索门店/会员");
        searchEd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = searchEd.getText().toString().trim();
                    if (viewPager.getCurrentItem() == 0){
                        merchantBillFragment.setSearch(search);
                    }else {
                        memberBillFragment.setSearch(search);
                    }
                    externalView.setVisibility(View.GONE);
                    searchEd.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.tv_search,R.id.external_view})
    protected void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_search:
                externalView.setVisibility(View.VISIBLE);
                break;
            case R.id.external_view:
                externalView.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchEd.getWindowToken(),0);
                break;
        }
    }

}
