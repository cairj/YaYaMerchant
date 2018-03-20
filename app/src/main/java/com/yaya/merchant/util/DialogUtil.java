package com.yaya.merchant.util;

import android.content.Context;
import android.view.View;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.widgets.dialog.DoubleBtnDialog;
import com.yaya.merchant.widgets.dialog.SingleBtnDialog;

/**
 * Created by admin on 2018/3/21.
 */

public class DialogUtil {

    //密码修改成功对话框
    public static void changePasswordSuccessDialog(Context context, SingleBtnDialog.OnClickListener listener) {
        SingleBtnDialog dialog = new SingleBtnDialog(context);
        dialog.getTitleTv().setVisibility(View.GONE);
        dialog.getContentTv().setText("密码修改成功");
        dialog.getSubmitBtnTv().setText("我知道了");
        dialog.setListener(listener);
        dialog.show();
    }
}
