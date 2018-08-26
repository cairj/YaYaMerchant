package com.yaya.merchant.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.LoginAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.login.TokenData;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 输入验证码界面
 */

public class ProvingActivity extends BaseActivity {

    private String phone;
    private String code;
    private boolean provingSucceed;
    private String userName;
    private int memberType;

    private int countDown = 60;//倒计时
    private final int WHAT_COUNT_DOWN = 1;

    public static void open(Context context, String phone, String userName, int memberType) {
        Intent intent = new Intent(context, ProvingActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("userName", userName);
        intent.putExtra("memberType", memberType);
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

        postTv.setText(String.format("重新发送(%d)",countDown));
        handler.sendEmptyMessageDelayed(WHAT_COUNT_DOWN,1000);
        postTv.setEnabled(false);
        contentTv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        userName = getIntent().getStringExtra("userName");
        phone = getIntent().getStringExtra("phone").trim();
        memberType = getIntent().getIntExtra("memberType",0);

        contentTv.setText(String.format("验证码已发送至%s****%s",phone.substring(0,3),phone.substring(phone.length()-4)));
    }

    @OnClick({R.id.proving_tv_post, R.id.proving_tv_next,R.id.tv_action_back})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.proving_tv_post:
                sendMessage();
                break;
            case R.id.proving_tv_next:
                LoginAction.verification(userName, codeEdit.getText().toString().trim(),
                        new GsonCallback<TokenData>(TokenData.class) {
                            @Override
                            public void onSucceed(JsonResponse<TokenData> response) {
                                String token = response.getResultData().getToken();
                                ResetPasswordActivity.open(ProvingActivity.this,token);
                                finish();
                            }
                        });
                break;
            case R.id.tv_action_back:
                finish();
                break;
        }
    }

    //发送验证码
    private void sendMessage(){
        postTv.setText(String.format("重新发送(%d)",countDown));
        handler.sendEmptyMessageDelayed(WHAT_COUNT_DOWN,1000);
        postTv.setEnabled(false);
        LoginAction.sendMessage(userName,memberType,
                new GsonCallback<String>(String.class) {
                    @Override
                    public void onSucceed(JsonResponse<String> response) {
                    }
                });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WHAT_COUNT_DOWN:
                    countDown--;
                    if (countDown>0){
                        postTv.setText(String.format("重新发送(%d)",countDown));
                        handler.sendEmptyMessageDelayed(WHAT_COUNT_DOWN,1000);
                    }else {
                        postTv.setText("发送");
                        postTv.setEnabled(true);
                        countDown = 60;
                    }
                    break;
            }
        }
    };

}
