package com.yaya.merchant.activity.login;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.LoginAction;
import com.yaya.merchant.activity.MainActivity;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.login.TokenData;
import com.yaya.merchant.interfaces.OnEditTextChangeListener;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.Constants;
import com.yaya.merchant.util.DeviceParamsUtil;
import com.yaya.merchant.util.LoadingUtil;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.util.sp.SPUtil;
import com.yaya.merchant.util.sp.SpKeys;
import com.yaya.merchant.widgets.dialog.SingleBtnDialog;

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

    @BindView(R.id.login_rb_merchant)
    protected RadioButton merchantRb;
    @BindView(R.id.login_rb_agent)
    protected RadioButton agentRb;

    private int memberType;
    private String xinGeToken;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        initEditView();
        merchantRb.setChecked(true);
    }

    @Override
    protected void initData() {
        super.initData();
        getXinGeToken();
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
                login();
                break;
            case R.id.login_tv_forget_password:
                openActivity(InputUserNameActivity.class);
                break;
            case R.id.login_tv_register_merchant:
                openActivity(RegisterMerchantActivity.class);
                break;
        }
    }

    private void login(){
        if (!merchantRb.isChecked()&&!agentRb.isChecked()){
            ToastUtil.toast("请选择是商户或代理" );
            return;
        }
        memberType = merchantRb.isChecked()? Constants.MEMBER_TYPE_MERCHANT:Constants.MEMBER_TYPE_AGENT;
        /*String udid = DeviceParamsUtil.getUdid(this);
        if (!TextUtils.isEmpty(udid)) {
            login(udid);
        }*/
        loginRequest();
    }

    private void getXinGeToken(){
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                xinGeToken = data.toString();
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                getXinGeToken();
            }
        });
    }

    private void loginRequest(){
        if (TextUtils.isEmpty(xinGeToken)){
            ToastUtil.toast("正在注册信鸽推送，请稍后再试...");
            return;
        }
        LoadingUtil.showAsyncProgressDialog(this);
        LoginAction.login(userEditView.getText().toString().trim(),
                passwordEditView.getText().toString().trim(), xinGeToken,
                memberType,new GsonCallback<TokenData>(TokenData.class) {

                    @Override
                    public void onSucceed(JsonResponse<TokenData> response) {
                        LoadingUtil.hideProcessingIndicator();
                        SPUtil.putBoolean(SpKeys.IS_LOGIN, true);
                        SPUtil.putString(SpKeys.TOKEN, response.getResultData().getToken());
                        SPUtil.putInt(SpKeys.USER_TYPE, memberType);
                        openActivity(MainActivity.class, true);
                    }

                    @Override
                    public void onFailed(JsonResponse<TokenData> response) {
                        LoadingUtil.hideProcessingIndicator();
                        SingleBtnDialog dialog = new SingleBtnDialog(LoginActivity.this,R.layout.dialog_text_single_btn);
                        dialog.getContentTv().setText(response.getMsg());
                        dialog.show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (!SPUtil.getBoolean(SpKeys.IS_LOGIN)){
            XGPushManager.registerPush(this,"*");
            XGPushManager.unregisterPush(this);
        }
        super.onDestroy();
    }
}
