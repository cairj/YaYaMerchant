package com.yaya.merchant.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created by admin on 2018/3/17.
 */

public class TextFontUtil {

    public static void setTextColor(TextView tv, String content, int color, int startIndex, int endIndex) {
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(color),
                startIndex, endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
    }

}
