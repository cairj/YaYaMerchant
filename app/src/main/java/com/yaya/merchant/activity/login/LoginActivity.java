package com.yaya.merchant.activity.login;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.LoginAction;
import com.yaya.merchant.activity.MainActivity;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.interfaces.OnEditTextChangeListener;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.util.sp.SPUtil;
import com.yaya.merchant.util.sp.SpKeys;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录界面
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_ed_user)
    protected EditText userEditView;
    @BindView(R.id.login_ed_password)
    protected EditText passwordEditView;
    @BindView(R.id.login_iv_user_clear)
    protected ImageView userClearIv;
    @BindView(R.id.login_iv_password_clear)
    protected ImageView passwordClearIv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void beforeSetContent() {
        if (SPUtil.getBoolean(SpKeys.IS_LOGIN)){
            openActivity(MainActivity.class,true);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setWindowStatusBarColor(this,R.color.white);
        initEditView();
    }

    //初始化输入框
    private void initEditView() {
        userEditView.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userClearIv.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });

        passwordEditView.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordClearIv.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });
    }
    @OnClick({R.id.login_iv_user_clear, R.id.login_iv_password_clear, R.id.login_tv_submit,
            R.id.login_tv_forget_password, R.id.login_tv_register_merchant})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_iv_user_clear:
                userEditView.setText("");
                break;
            case R.id.login_iv_password_clear:
                passwordEditView.setText("");
                break;
            case R.id.login_tv_submit:
                LoginAction.login(userEditView.getText().toString().trim(),
                        passwordEditView.getText().toString().trim(), new GsonCallback<String>(String.class) {
                            @Override
                            public void onSucceed(JsonResponse<String> response) {
                                SPUtil.putBoolean(SpKeys.IS_LOGIN, true);
                                SPUtil.putString(SpKeys.TOKEN, response.getData().getData());
                                openActivity(MainActivity.class, true);
                            }

                            @Override
                            public void onFailed(JsonResponse<String> response) {
                                ToastUtil.toast(response.getError().getMessage());
                            }
                        });
                break;
            case R.id.login_tv_forget_password:
                openActivity(InputUserNameActivity.class);
                break;
            case R.id.login_tv_register_merchant:
                break;
        }
    }
}
