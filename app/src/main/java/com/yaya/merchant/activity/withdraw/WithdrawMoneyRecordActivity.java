package com.yaya.merchant.activity.withdraw;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.action.WithDrawMoneyAction;
import com.yaya.merchant.activity.login.RegisterMerchantActivity;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.withdraw.WithdrawMoneyRecord;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.widgets.adapter.WithdrawMoneyAdapter;
import com.yaya.merchant.widgets.dialog.DoubleBtnDialog;

/**
 * Created by 蔡蓉婕 on 2018/3/15.
 */

public class WithdrawMoneyRecordActivity extends BasePtrRecycleActivity<WithdrawMoneyRecord> {

    private String cashoutType;
    private String status;
    private String startTime;
    private String endTime;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_withdraw_record;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new WithdrawMoneyAdapter(mDataList);
    }

    @Override
    protected JsonResponse<BaseRowData<WithdrawMoneyRecord>> getData() throws Exception {
        return WithDrawMoneyAction.getWithdrawMoneyRecord(cashoutType, status, startTime, endTime,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("提现记录");
    }

    @Override
    protected void rightClick() {
        getServicePhone();
    }

    private void getServicePhone() {
        UserAction.getServicePhone(new GsonCallback<String>(String.class) {
            @Override
            public void onSucceed(final JsonResponse<String> response) {
                final DoubleBtnDialog dialog = new DoubleBtnDialog(WithdrawMoneyRecordActivity.this);
                ViewGroup.LayoutParams lp = dialog.getPictureIv().getLayoutParams();
                lp.height = DpPxUtil.dp2px(130);
                dialog.getPictureIv().setLayoutParams(lp);
                dialog.getTitleTv().setVisibility(View.GONE);
                dialog.getContentTv().setText("预计24小时到账\n" + "联系电话：" + response.getData());
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
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
    }

}
