package com.yaya.merchant.activity.user;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.interfaces.OnEditTextChangeListener;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.DialogUtil;
import com.yaya.merchant.widgets.dialog.SingleBtnDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改密码
 */

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.ed_old_password)
    protected EditText oldPasswordEd;
    @BindView(R.id.ed_new_password)
    protected EditText newPasswordEd;
    @BindView(R.id.ed_confirm_password)
    protected EditText confirmPasswordEd;
    @BindView(R.id.iv_old_password_clear)
    protected ImageView oldPasswordClearIv;
    @BindView(R.id.iv_new_password_clear)
    protected ImageView newPasswordClearIv;
    @BindView(R.id.iv_confirm_password_clear)
    protected ImageView confirmPasswordClearIv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView() {
        super.initView();
        initEditView();
    }

    //初始化输入框
    private void initEditView() {
        oldPasswordEd.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                oldPasswordClearIv.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });

        newPasswordEd.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newPasswordClearIv.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });
        confirmPasswordEd.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmPasswordClearIv.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @OnClick({R.id.tv_submit_change,R.id.iv_old_password_clear,R.id.iv_new_password_clear,R.id.iv_confirm_password_clear})
    protected void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_submit_change:
                UserAction.changePassword(oldPasswordEd.getText().toString().trim(), newPasswordEd.getText().toString().trim(),
                        confirmPasswordEd.getText().toString().trim(),
                        new GsonCallback<String>(String.class) {
                            @Override
                            public void onSucceed(JsonResponse<String> response) {
                                DialogUtil.changePasswordSuccessDialog(ChangePasswordActivity.this, new SingleBtnDialog.OnClickListener() {
                                    @Override
                                    public void submit() {
                                        finish();
                                    }
                                });
                            }
                        });
                break;
            case R.id.iv_old_password_clear:
                oldPasswordEd.setText("");
                break;
            case R.id.iv_new_password_clear:
                newPasswordEd.setText("");
                break;
            case R.id.iv_confirm_password_clear:
                confirmPasswordEd.setText("");
                break;
        }
    }

}
