package com.yaya.merchant.activity.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.main.UserData;
import com.yaya.merchant.util.UserHelper;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/18.
 */

public class InformationActivity extends BaseActivity {

    @BindView(R.id.information_iv_head)
    protected ImageView headIv;
    @BindView(R.id.information_tv_username)
    protected TextView usernameTv;
    @BindView(R.id.information_tv_company_name)
    protected TextView companyNameTv;
    @BindView(R.id.information_tv_phone_number)
    protected TextView phoneNumberTv;
    @BindView(R.id.information_tv_admissible_business)
    protected TextView admissibleBusinessTv;
    @BindView(R.id.information_tv_create_time)
    protected TextView createTimeTv;

    private UserData userData;

    public static void open(Context context, UserData userData) {
        Intent intent = new Intent(context, InformationActivity.class);
        intent.putExtra("userData", userData);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_information;
    }

    @Override
    protected void initData() {
        super.initData();
        userData = (UserData) getIntent().getSerializableExtra("userData");
        GlideLoaderHelper.loadAvatar(userData.getHeadImgUrl(), headIv);
        usernameTv.setText(userData.getName());
        companyNameTv.setText(userData.getCompanyName());
        phoneNumberTv.setText(userData.getPhone());
        admissibleBusinessTv.setText(userData.getAgentName());
        createTimeTv.setText(userData.getRegTime());
    }

    @OnClick({R.id.fl_change_password, R.id.tv_logout})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_change_password:
                openActivity(ChangePasswordActivity.class);
                break;
            case R.id.tv_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("确认退出").setMessage("是否确认退出？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                UserHelper.logout(InformationActivity.this);
                            }
                        })
                        .setNegativeButton("否",null);
                builder.show();
                break;
        }
    }
}
