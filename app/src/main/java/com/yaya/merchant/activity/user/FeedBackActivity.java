package com.yaya.merchant.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.interfaces.OnEditTextChangeListener;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.ImagePickUtil;
import com.yaya.merchant.util.LoadingUtil;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.widgets.adapter.PostImgAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/18.
 */

public class FeedBackActivity extends BaseActivity {

    private static final int MAX_CONTENT_SIZE = 100;

    @BindView(R.id.ed_input_feed_back)
    protected EditText feedBackEdit;
    @BindView(R.id.tv_input_size)
    protected TextView inputSizeTv;
    @BindView(R.id.rv_images)
    protected GridView imagesRv;

    protected PostImgAdapter mImgAdapter;
    private ArrayList<LocalMedia> mLocalImgList = new ArrayList<>();
    //最后一张图片的位置，显示加号
    private int addImgPos = -1;
    private String ADD_IMG_POS_TAG = "ADD_IMG_POS_TAG";
    private String IMG_LIST_TAG = "IMG_LIST_TAG";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("意见反馈");
        setActionBarRight("提交");
        initInput();
        initImgGv();
    }

    @Override
    protected void rightClick() {
        postFeedBack();
    }

    private void postFeedBack(){
        LoadingUtil.showAsyncProgressDialog(this);
        UserAction.postFeedBack(feedBackEdit.getText().toString().trim(), mLocalImgList, new GsonCallback<String>(String.class) {
            @Override
            public void onSucceed(JsonResponse<String> response) {
                LoadingUtil.hideProcessingIndicator();
                ToastUtil.toast(response.getData().getData());
            }
        });
    }

    private void initInput() {
        feedBackEdit.setMaxEms(MAX_CONTENT_SIZE);
        feedBackEdit.addTextChangedListener(new OnEditTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputSizeTv.setText(String.format("%d/%d",
                        feedBackEdit.getText().toString().trim().length(), MAX_CONTENT_SIZE));
            }
        });
    }

    //设置图片列表
    protected void initImgGv() {
        if (imagesRv != null) {
            mImgAdapter = new PostImgAdapter(this, mLocalImgList);
            mImgAdapter.setOnItemClickListener(new PostImgAdapter.OnItemClickListener() {
                @Override
                public void addPhoto() {
                    pickPhoto();
                }
            });
            imagesRv.setAdapter(mImgAdapter);
        }
    }

    //从相册选区照片
    protected void pickPhoto() {
        ImagePickUtil.openPictureSelectorActivity(this, mLocalImgList);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (imagesRv != null) {
            //保存imgGv最后一个可见的位置
            addImgPos = imagesRv.getLastVisiblePosition();
            outState.putInt(ADD_IMG_POS_TAG, addImgPos);
            //保存imgList
            outState.putSerializable(IMG_LIST_TAG, mLocalImgList);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        addImgPos = savedInstanceState.getInt(ADD_IMG_POS_TAG);
        ArrayList<LocalMedia> temp = (ArrayList<LocalMedia>) savedInstanceState.getSerializable(IMG_LIST_TAG);
        mLocalImgList.addAll(temp);
        mImgAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 相册选取
            if (data != null && data.getExtras() != null){
                List<LocalMedia> mediaList = PictureSelector.obtainMultipleResult(data);
                mLocalImgList.clear();
                mLocalImgList.addAll(mediaList);
                mImgAdapter.notifyDataSetChanged();
            }
        }
    }

}
