package com.yaya.merchant.activity.user;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.account.Merchant;
import com.yaya.merchant.interfaces.OnEditTextChangeListener;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.widgets.adapter.SearchMerchantAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/18.
 */

public class ChangeMerchantPushActivity extends BasePtrRecycleActivity<Merchant> {

    private String searchKey;

    private InputMethodManager imm;

    @BindView(R.id.search_ed_input)
    protected EditText inputSearchEt;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_change_merchant_push;
    }

    @Override
    protected void initView() {
        super.initView();
        initEditText();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /*初始化输入框*/
    private void initEditText() {
        inputSearchEt.clearFocus();
        inputSearchEt.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchKey = charSequence.toString();
                refresh();
            }
        });
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new SearchMerchantAdapter(mDataList);
    }

    @Override
    protected JsonResponse<BaseRowData<Merchant>> getData() throws Exception {
        return UserAction.getMerchantVoiceSettingList(searchKey, String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void setData(List<Merchant> data) {
        this.mDataList.addAll(data);
        for (Merchant merchant : data) {
            if (merchant.isVoice()) {
                merchant.setSelected(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.tv_confirm_choose, R.id.search_tv_cancel})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm_choose:
                try {
                    confirmChoose();
                } catch (JSONException e) {
                }
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

    //确认设置门店语音
    private void confirmChoose() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Merchant merchant : mDataList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", String.valueOf(merchant.getId()))
                    .put("isVoice", String.valueOf(merchant.isSelected()));
            jsonArray.put(jsonObject);
        }
        UserAction.setMerchantVoice(jsonArray, new GsonCallback<String>(String.class) {
            @Override
            public void onSucceed(JsonResponse<String> response) {
                if (Boolean.parseBoolean(response.getData().getData())) {
                    ToastUtil.toast("成功");
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

}
