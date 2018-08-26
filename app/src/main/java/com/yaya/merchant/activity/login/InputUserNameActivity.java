package com.yaya.merchant.activity.login;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.LoginAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.login.TokenData;
import com.yaya.merchant.interfaces.OnEditTextChangeListener;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.Constants;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 输入用户名获取手机号界面
 */

public class InputUserNameActivity extends BaseActivity {

    @BindView(R.id.input_edit_user)
    protected EditText userNameEdit;
    @BindView(R.id.input_iv_user_clear)
    protected ImageView userNameClearIv;

    @BindView(R.id.login_rb_merchant)
    protected RadioButton merchantRb;
    @BindView(R.id.login_rb_agent)
    protected RadioButton agentRb;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_input_username;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setWindowStatusBarColor(this,R.color.white);
        initEditView();
    }

    //初始化输入框
    private void initEditView() {
        userNameEdit.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userNameClearIv.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @OnClick({R.id.input_tv_next,R.id.input_iv_user_clear,R.id.tv_action_back})
    protected void onClick(View view){
        switch (view.getId()) {
            case R.id.input_iv_user_clear:
                userNameEdit.setText("");
                break;
            case R.id.input_tv_next:
                if (!merchantRb.isChecked()&&!agentRb.isChecked()){
                    ToastUtil.toast("请选择是商户或代理" );
                    return;
                }
                final int memberType = merchantRb.isChecked()? Constants.MEMBER_TYPE_MERCHANT:Constants.MEMBER_TYPE_AGENT;
                final String userName = userNameEdit.getText().toString().trim();
                LoginAction.sendMessage(userName,memberType,
                        new GsonCallback<TokenData>(TokenData.class) {
                            @Override
                            public void onSucceed(JsonResponse<TokenData> response) {
                                String tel = response.getResultData().getTel();
                                ProvingActivity.open(InputUserNameActivity.this, tel, userName, memberType);
                                finish();
                            }
                        });
                break;
            case R.id.tv_action_back:
                finish();
                break;
        }
    }

}
