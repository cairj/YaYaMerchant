package com.yaya.merchant.util;

import android.view.View;

/**
 * Created by 蔡蓉婕 on 2018/3/9.
 */

public class GitViewUtils {
    // 通过一个viewId来获取一个view
    public static <T extends View> T findViewById(View container, int viewId) {
        return (T) container.findViewById(viewId);
    }
}
