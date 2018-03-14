package com.yaya.merchant.activity.account;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.interfaces.OnEditTextChangeListener;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.widgets.adapter.SearchMerchantAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/11.
 */

public class SearchMerchantActivity extends BaseActivity {

    public static final String RETURN_SELECT_MERCHANT_ID = "RETURN_SELECT_MERCHANT_ID";
    private String selectMerchantId = "";

    public static Intent openSearchIntent(Context context, String selectMerchantId) {
        Intent intent = new Intent(context, SearchMerchantActivity.class);
        intent.putExtra("selectMerchantId", selectMerchantId);
        return intent;
    }

    private SearchMerchantAdapter mAdapter;
    private ArrayList<Merchant> mData = new ArrayList<>();

    private String searchKey;

    private InputMethodManager imm;

    @BindView(R.id.recycler_view)
    protected RecyclerView contentRv;
    @BindView(R.id.toggle_all_merchant)
    protected ToggleButton allMerchantToggleBtn;
    @BindView(R.id.search_ed_input)
    protected EditText inputSearchEt;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search_merchant_employee;
    }

    @Override
    protected void initView() {
        initRecyclerView();
        initEditText();
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        selectMerchantId = getIntent().getStringExtra("selectMerchantId");
    }

    /*初始化列表*/
    private void initRecyclerView(){
        mAdapter = new SearchMerchantAdapter(mData);
        mAdapter.setListener(new SearchMerchantAdapter.OnItemClickListener() {
            @Override
            public void onClick(Merchant merchant) {
                merchant.setSelected(!merchant.isSelected());
                mAdapter.notifyDataSetChanged();
                setSelectMerchantId();
            }
        });
        contentRv.setAdapter(mAdapter);
        contentRv.setLayoutManager(new LinearLayoutManager(this));
        contentRv.addItemDecoration(addItemDecoration());
    }

    protected RecyclerView.ItemDecoration addItemDecoration() {
        RecyclerView.ItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.gray_F6F7F9))
                .sizeResId(R.dimen.divider_height_1dp)
                /*.marginResId(R.dimen.margin_edge)*/
                .build();
        return decoration;
    }

    /*初始化输入框*/
    private void initEditText(){
        inputSearchEt.clearFocus();
        inputSearchEt.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchKey = charSequence.toString();
                initData();
            }
        });
    }

    @Override
    protected void initData() {
        Type type = new TypeToken<BaseRowData<Merchant>>() {
        }.getType();
        MainDataAction.searchMerchant(searchKey, new GsonCallback<BaseRowData<Merchant>>(type) {
            @Override
            public void onSucceed(JsonResponse<BaseRowData<Merchant>> response) {
                mData.clear();
                mData.addAll(response.getData().getData().getRows());
                checkSelectedMerchant();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    //根据选择的商家id字符串selectMerchantId判断是否是全部门店
    private void checkSelectedMerchant() {
        if (!TextUtils.isEmpty(selectMerchantId)) {
            String[] merchantIds = selectMerchantId.split(",");
            int selectCount = 0;
            for (int i = 0; i < merchantIds.length; i++) {
                for (Merchant merchant : mData) {
                    if (merchant.getId() == Integer.parseInt(merchantIds[i])) {
                        merchant.setSelected(true);
                        selectCount++;
                    }
                }
            }
            judgeAllMerchantSelected(selectCount);
        }
    }

    //获取选择的商家id，组成字符串selectMerchantId
    private void setSelectMerchantId() {
        int selectCount = 0;
        selectMerchantId = "";
        for (Merchant merchant : mData) {
            if (merchant.isSelected()) {
                selectMerchantId = (TextUtils.isEmpty(selectMerchantId) ? "" : selectMerchantId + ",") + merchant.getId();
                selectCount++;
            }
        }
        judgeAllMerchantSelected(selectCount);
    }

    //根据选择的个数selectCount判断是否全选了
    private void judgeAllMerchantSelected(int selectCount) {
        if (selectCount == mData.size()) {//全选了
            allMerchantToggleBtn.setChecked(true);
        } else {
            allMerchantToggleBtn.setChecked(false);
        }
    }

    @OnClick({R.id.toggle_all_merchant, R.id.tv_confirm_choose,R.id.search_tv_cancel})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.toggle_all_merchant:
                for (Merchant merchant : mData) {
                    merchant.setSelected(allMerchantToggleBtn.isChecked());
                }
                mAdapter.notifyDataSetChanged();
                setSelectMerchantId();
                break;
            case R.id.tv_confirm_choose:
                Intent intent = new Intent();
                intent.putExtra(RETURN_SELECT_MERCHANT_ID, selectMerchantId);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.search_tv_cancel:
                imm.hideSoftInputFromWindow(inputSearchEt.getWindowToken(), 0);
                if (!TextUtils.isEmpty(inputSearchEt.getText().toString())) {
                    inputSearchEt.setText("");
                } else {
                    finish();
                }
                break;
        }
    }

}
