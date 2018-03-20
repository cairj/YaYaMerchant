package com.yaya.merchant.activity.user;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.CustomCaptureManager;
import com.yaya.merchant.util.ToastUtil;

import butterknife.BindView;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        captureManager = new CustomCaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.setCallback(new CustomCaptureManager.OnGetResultCallback() {
            @Override
            public void parseResult(BarcodeResult rawResult) {
                UserAction.verification(Urls.VERIFICATION_INDEX, rawResult.getText(), new GsonCallback() {
                });
            }
        });
        captureManager.decode();
    }
}
