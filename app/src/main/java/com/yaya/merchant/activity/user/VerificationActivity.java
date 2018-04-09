package com.yaya.merchant.activity.user;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.user.VerificationInfo;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.CustomCaptureManager;
import com.yaya.merchant.util.LoadingUtil;

import butterknife.BindView;
import okhttp3.Request;

/**
 * Created by admin on 2018/3/19.
 */

public class VerificationActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_verification;
    }

    @BindView(R.id.dbv_custom)
    DecoratedBarcodeView mDBV;

    private CustomCaptureManager captureManager;

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("核销");
        captureManager = new CustomCaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.setCallback(new CustomCaptureManager.OnGetResultCallback() {
            @Override
            public void parseResult(final BarcodeResult rawResult) {
                UserAction.verification(Urls.VERIFICATION_INDEX, rawResult.getText(), new GsonCallback<VerificationInfo>(VerificationInfo.class) {

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        LoadingUtil.showAsyncProgressDialog(VerificationActivity.this,"正在进行核销");
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        LoadingUtil.hideProcessingIndicator();
                        captureManager.onResume();
                        captureManager.decode();
                    }

                    @Override
                    public void onSucceed(JsonResponse<VerificationInfo> response) {
                        VerificationInfo info = response.getData().getData();
                        info.setVerificationSn(rawResult.getText());
                        VerificationResultActivity.open(VerificationActivity.this,info);
                    }

                    @Override
                    public void onFailed(JsonResponse<VerificationInfo> response) {
                        mDBV.setStatusText(response.getData().getError());
                    }
                });
            }
        });
        captureManager.decode();
    }
}
