package com.yaya.merchant.activity.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.LoginAction;
import com.yaya.merchant.base.BaseActivity;
import com.yaya.merchant.interfaces.OnEditTextChangeListener;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/5.
 */

public class ChangePasswordActivity extends BaseActivity {
    private String userId;

    @BindView(R.id.reset_password_ed_new_word)
    protected EditText newPasswordEdit;
    @BindView(R.id.reset_password_iv_new_word_clear)
    protected ImageView newPasswordClearIv;
    @BindView(R.id.reset_password_ed_confirm_password)
    protected EditText confirmPasswordEdit;
    @BindView(R.id.reset_password_iv_confirm_password_clear)
    protected ImageView confirmPasswordClearIv;

    public static void open(Context context, String userId) {
        Intent intent = new Intent(context, ProvingActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        initEditView();
    }

    //初始化输入框
    private void initEditView() {
        newPasswordEdit.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newPasswordClearIv.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });

        confirmPasswordEdit.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmPasswordClearIv.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @OnClick({R.id.reset_password_iv_new_word_clear,R.id.reset_password_iv_confirm_password_clear,
            R.id.input_iv_user_clear,R.id.tv_action_back})
    protected void onClick(View view){
        switch (view.getId()) {
            case R.id.reset_password_iv_new_word_clear:
                newPasswordEdit.setText("");
                break;
            case R.id.reset_password_iv_confirm_password_clear:
                confirmPasswordEdit.setText("");
                break;
            case R.id.input_tv_next:
                LoginAction.changePassword(newPasswordEdit.getText().toString().trim(),
                        confirmPasswordEdit.getText().toString().trim(), userId,
                        new GsonCallback<String>(String.class) {
                            @Override
                            public void onSucceed(JsonResponse<String> response) {

                            }
                        });
                break;
            case R.id.tv_action_back:
                finish();
                break;
        }
    }

}
