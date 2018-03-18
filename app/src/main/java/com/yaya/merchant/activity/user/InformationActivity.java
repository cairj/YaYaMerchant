package com.yaya.merchant.activity.user;

import android.widget.ImageView;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.user.Information;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import butterknife.BindView;

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
    @BindView(R.id.information_tv_valid_date)
    protected TextView validDateTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_information;
    }

    @Override
    protected void initData() {
        super.initData();
        UserAction.getInformation(new GsonCallback<Information>(Information.class) {
            @Override
            public void onSucceed(JsonResponse<Information> response) {
                Information information = response.getData().getData();
                GlideLoaderHelper.loadImg(information.getHeadImg(), headIv);
                usernameTv.setText(information.getCorporation());
                companyNameTv.setText(information.getName());
                phoneNumberTv.setText(information.getPhone());
                admissibleBusinessTv.setText(information.getAdmissibleBusiness());
                createTimeTv.setText(information.getCreationTime().replace("T"," "));
            }
        });
    }
}
