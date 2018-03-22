package com.yaya.merchant.activity.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.activity.MainActivity;
import com.yaya.merchant.activity.login.LoginActivity;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.main.UserData;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.AppManager;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.util.ImagePickUtil;
import com.yaya.merchant.util.JPushUtil;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;
import com.yaya.merchant.util.sp.SPUtil;
import com.yaya.merchant.util.sp.SpKeys;
import com.yaya.merchant.widgets.dialog.DoubleBtnDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */

public class SettingActivity extends BaseActivity {

    private UserData userData;
    private ArrayList<LocalMedia> selectImgList = new ArrayList<>();
    private String ADD_IMG_POS_TAG = "ADD_IMG_POS_TAG";
    private String IMG_LIST_TAG = "IMG_LIST_TAG";

    @BindView(R.id.iv_pic)
    protected ImageView picIv;

    public static void open(Context context, UserData userData) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.putExtra("userData", userData);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        super.initView();
        userData = (UserData) getIntent().getSerializableExtra("userData");
        GlideLoaderHelper.loadAvatar(userData.getHeadImgUrl(), picIv);
    }

    @OnClick({R.id.fl_change_pic, R.id.tv_change_password, R.id.tv_logout})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_change_password:
                openActivity(ChangePasswordActivity.class);
                break;
            case R.id.fl_change_pic:
                ImagePickUtil.openPictureSelectorActivity(this, selectImgList, 1, false);
                break;
            case R.id.tv_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("确认退出").setMessage("是否确认退出？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SPUtil.putBoolean(SpKeys.IS_LOGIN, false);
                                SPUtil.putString(SpKeys.TOKEN, "");
                                JPushUtil.deleteAlias(SettingActivity.this);
                                JPushUtil.cleanTags(SettingActivity.this);
                                AppManager.getAppManager().finishAllActivity();
                                openActivity(LoginActivity.class, true);
                            }
                        })
                        .setNegativeButton("否",null);
                builder.show();
                break;
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存imgList
        outState.putSerializable(IMG_LIST_TAG, selectImgList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<LocalMedia> temp = (ArrayList<LocalMedia>) savedInstanceState.getSerializable(IMG_LIST_TAG);
        selectImgList.addAll(temp);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 相册选取
            if (data != null && data.getExtras() != null) {
                List<LocalMedia> mediaList = PictureSelector.obtainMultipleResult(data);
                selectImgList.clear();
                selectImgList.addAll(mediaList);
            }
            judgeChangePicDialog();
        }
        if (requestCode == PictureConfig.REQUEST_CAMERA) {//拍照得到的

        }
    }

    private void judgeChangePicDialog() {
        final DoubleBtnDialog dialog = new DoubleBtnDialog(this);
        dialog.setCancelable(false);
        GlideLoaderHelper.loadAvatar(new File(selectImgList.get(0).getPath()), dialog.getPictureIv());
        dialog.getTitleTv().setText("是否确定更改成这张图");
        dialog.getContentTv().setVisibility(View.GONE);
        dialog.getLeftBtnTv().setText("取消");
        dialog.getRightBtnTv().setText("确定");
        dialog.setWidth(DpPxUtil.dp2px(248));
        dialog.setListener(new DoubleBtnDialog.OnClickListener() {
            @Override
            public void leftClick() {
                dialog.dismiss();
            }

            @Override
            public void rightClick() {
                UserAction.changeProfilePic(selectImgList.get(0), new GsonCallback<String>(String.class) {
                    @Override
                    public void onSucceed(JsonResponse<String> response) {
                        ToastUtil.toast(response.getData().getData());
                        GlideLoaderHelper.loadAvatar(new File(selectImgList.get(0).getPath()), picIv);
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }

}
