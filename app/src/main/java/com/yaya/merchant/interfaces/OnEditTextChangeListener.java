package com.yaya.merchant.interfaces;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by admin on 2018/3/4.
 */

public abstract class OnEditTextChangeListener implements TextWatcher{

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
