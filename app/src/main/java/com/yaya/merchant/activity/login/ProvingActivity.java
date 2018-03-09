package com.yaya.merchant.activity.login;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.LoginAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.login.SendMessageData;
import com.yaya.merchant.interfaces.OnEditTextChangeListener;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 输入验证码界面
 */

public class ProvingActivity extends BaseActivity {

    private String phone;
    private String code;
    private boolean provingSucceed;
    private String userId;

    public static void open(Context context, String phone, String userId) {
        Intent intent = new Intent(context, ProvingActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    @BindView(R.id.proving_edit_code)
    protected EditText codeEdit;
    @BindView(R.id.proving_tv_content)
    protected TextView contentTv;
    @BindView(R.id.proving_tv_next)
    protected TextView nextTv;
    @BindView(R.id.proving_tv_post)
    protected TextView postTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_proving;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        initEditView();
    }

    @Override
    protected void initData() {
        userId = getIntent().getStringExtra("userId");
        phone = getIntent().getStringExtra("phone");
        if (!TextUtils.isEmpty(phone)) {
            LoginAction.sendMessage(phone, new GsonCallback<SendMessageData>(SendMessageData.class) {
                @Override
                public void onSucceed(JsonResponse<SendMessageData> response) {
                    ToastUtil.toast(response.getData().getData().getMsg());
                    code = response.getData().getData().getCode();
                    contentTv.setText(String.format("验证码已发送至%s", phone));
                    contentTv.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    //初始化输入框
    private void initEditView() {
        codeEdit.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                postTv.setEnabled(codeEdit.getText().length() > 0);
            }
        });
    }

    @OnClick({R.id.proving_tv_post, R.id.proving_tv_next,R.id.tv_action_back})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.proving_tv_post:
                String inputCode = codeEdit.getText().toString().trim();
                provingSucceed = inputCode.equals(code);
                break;
            case R.id.proving_tv_next:
                if (provingSucceed){

                }
                break;
            case R.id.tv_action_back:
                finish();
                break;
        }
    }

}
