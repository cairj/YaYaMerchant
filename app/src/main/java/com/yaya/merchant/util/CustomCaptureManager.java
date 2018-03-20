package com.yaya.merchant.util;

import android.app.Activity;

import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

/**
 * Created by 蔡蓉婕 on 2018/3/20.
 */

public class CustomCaptureManager extends CaptureManager {
    public CustomCaptureManager(Activity activity, DecoratedBarcodeView barcodeView) {
        super(activity, barcodeView);
    }

    @Override
    protected void returnResult(BarcodeResult rawResult) {
        if (callback!=null){
            callback.parseResult(rawResult);
        }
    }

    private OnGetResultCallback callback;

    public void setCallback(OnGetResultCallback callback) {
        this.callback = callback;
    }

    public interface OnGetResultCallback{
        void parseResult(BarcodeResult rawResult);
    }

}
