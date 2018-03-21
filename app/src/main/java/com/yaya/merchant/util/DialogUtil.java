package com.yaya.merchant.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.activity.withdraw.WithdrawMoneyRecordActivity;
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

    //联系客服对话框
    public static void chatToService(final Context context, final String content){
        UserAction.getServicePhone(new GsonCallback<String>(String.class) {
            @Override
            public void onSucceed(final JsonResponse<String> response) {
                final DoubleBtnDialog dialog = new DoubleBtnDialog(context);
                ViewGroup.LayoutParams lp = dialog.getPictureIv().getLayoutParams();
                lp.height = DpPxUtil.dp2px(130);
                dialog.getPictureIv().setLayoutParams(lp);
                dialog.getTitleTv().setVisibility(View.GONE);
                dialog.getContentTv().setText(content+ "联系电话：" + response.getData().getData());
                dialog.getLeftBtnTv().setText("取消");
                dialog.getRightBtnTv().setText("立即拨打");
                dialog.setWidth(DpPxUtil.dp2px(248));
                dialog.setListener(new DoubleBtnDialog.OnClickListener() {
                    @Override
                    public void leftClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void rightClick() {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + response.getData().getData()));
                        context.startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
    }
}
