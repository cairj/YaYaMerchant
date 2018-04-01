package com.yaya.merchant.activity.login;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.LoginAction;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.Region;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.util.LoadingUtil;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.widgets.dialog.DoubleBtnDialog;
import com.yaya.merchant.widgets.popupwindow.RegionPickerPopupWindow;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * 注册商户
 */

public class RegisterMerchantActivity extends BaseActivity {

    @BindView(R.id.ed_name)
    protected EditText nameEd;
    @BindView(R.id.ed_phone)
    protected EditText phoneEd;
    @BindView(R.id.ed_merchant_name)
    protected EditText merchantNameEd;
    @BindView(R.id.ed_address)
    protected EditText addressEd;
    @BindView(R.id.tv_city)
    protected TextView cityTv;

    RegionPickerPopupWindow regionPickerPopupWindow;
    private String merchantCity;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register_merchant;
    }

    @Override
    protected void initData() {
        super.initData();
        setActionBarTitle("商户开通申请");
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        regionPickerPopupWindow = new RegionPickerPopupWindow(this);
        regionPickerPopupWindow.setOnSubmitTvClickListener(new RegionPickerPopupWindow.OnSubmitTvClickListener() {
            @Override
            public void onSubmitTvClick(Region province, Region city, Region district) {
                merchantCity = RegionPickerPopupWindow.getAddress(province, city, district);
                cityTv.setText("店铺地址：" + merchantCity);
            }
        });
    }

    @OnClick({R.id.tv_city, R.id.tv_submit_register})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_city:
                regionPickerPopupWindow.show();
                break;
            case R.id.tv_submit_register:
                registerMerchant();
                break;
        }
    }

    private void registerMerchant(){
        new AsyncTask<String, Integer, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                LoadingUtil.showAsyncProgressDialog(RegisterMerchantActivity.this);
            }

            @Override
            protected String doInBackground(String... strings) {
                return LoginAction.registerMerchant(nameEd.getText().toString().trim(), phoneEd.getText().toString().trim(),
                        merchantNameEd.getText().toString().trim(), merchantCity, addressEd.getText().toString().trim());
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                LoadingUtil.hideProcessingIndicator();
                if (!TextUtils.isEmpty(s)) {
                    if (s.contains("成功")) {
                        getServicePhone();
                    }else {
                        ToastUtil.toast(s);
                    }
                }

            }
        }.execute("");
    }

    private void getServicePhone() {
        UserAction.getServicePhone(new GsonCallback<String>(String.class) {
            @Override
            public void onSucceed(final JsonResponse<String> response) {
                DoubleBtnDialog dialog = new DoubleBtnDialog(RegisterMerchantActivity.this);
                dialog.setCancelable(false);
                dialog.getTitleTv().setText("提交成功");
                dialog.getPictureIv().setImageResource(R.mipmap.ic_submit);
                dialog.getContentTv().setText("我们将会在24小时联系您\n" + "请注意接听");
                dialog.getLeftBtnTv().setText("取消");
                dialog.getRightBtnTv().setText("立即拨打");
                dialog.setWidth(DpPxUtil.dp2px(248));
                dialog.setListener(new DoubleBtnDialog.OnClickListener() {
                    @Override
                    public void leftClick() {
                        finish();
                    }

                    @Override
                    public void rightClick() {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + response.getData().getData()));
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
    }

}
