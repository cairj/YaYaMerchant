package com.yaya.merchant.widgets;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yaya.merchant.R;

/**
 *
 */
public class LoadingProgressDialog extends Dialog {

    private TextView loadingValue;

    public LoadingProgressDialog(Context context) {
        super(context, R.style.dialog_progress);

        setContentView(R.layout.loading_progress_dialog);
        loadingValue = (TextView) findViewById(R.id.loading_value);

    }

    public LoadingProgressDialog setLoadingText(String loading) {

        loadingValue.setVisibility(TextUtils.isEmpty(loading) ? View.GONE : View.VISIBLE);
        loadingValue.setText(loading);

        return this;
    }
}
