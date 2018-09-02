package com.yaya.merchant.activity.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.user.VoiceSetData;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.VoiceUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/18.
 */

public class VoiceSettingActivity extends BaseActivity {

    public static final int REQUEST_TO_CHANGE_MERCHANT_PUSH = 9999;

    @BindView(R.id.tg_btn_voice_alert)
    protected ToggleButton voiceAlertTgBtn;
    @BindView(R.id.tg_btn_alert_way_voice)
    protected ToggleButton alertWayVoiceTgBtn;
    @BindView(R.id.tg_btn_alert_way_push)
    protected ToggleButton alertWayPushTgBtn;
    @BindView(R.id.tv_receive_push_merchant)
    protected TextView receivePushTv;

    private String auditionMessage = "语音试听";//试听内容

    @Override
    protected int getContentViewId() {
        return R.layout.activity_voice_setting;
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("语音设置");
    }

    @Override
    protected void initData() {
        super.initData();
        UserAction.getVoiceSet(callback);
    }

    @OnClick({R.id.tg_btn_voice_alert, R.id.tg_btn_alert_way_voice,
            R.id.tg_btn_alert_way_push, /*R.id.fl_receive_push_merchant,*/ R.id.tv_audition})
    protected void onclick(View view) {
        switch (view.getId()) {
            case R.id.tg_btn_voice_alert:
                UserAction.setVoice(voiceAlertTgBtn.isChecked()?"2":"1",callback);
                break;
            case R.id.tg_btn_alert_way_voice:
                UserAction.setVoiceSound(alertWayVoiceTgBtn.isChecked()?"2":"1",callback);
                break;
            case R.id.tg_btn_alert_way_push:
                UserAction.setVoicePush(alertWayPushTgBtn.isChecked()?"2":"1",callback);
                break;
            case R.id.fl_receive_push_merchant:
                Intent intent = new Intent(this, ChangeMerchantPushActivity.class);
                startActivityForResult(intent, REQUEST_TO_CHANGE_MERCHANT_PUSH);
                break;
            case R.id.tv_audition:
                VoiceUtils.getInstance().speak(this, auditionMessage);
                break;
        }
    }

    GsonCallback<VoiceSetData> callback = new GsonCallback<VoiceSetData>(VoiceSetData.class) {
        @Override
        public void onSucceed(JsonResponse<VoiceSetData> response) {
            VoiceSetData data = response.getResultData();
            //auditionMessage = data.getAudition();
            voiceAlertTgBtn.setChecked("1".equals(data.getIsVoice()));
            alertWayPushTgBtn.setChecked("1".equals(data.getLetter()));
            alertWayVoiceTgBtn.setChecked("1".equals(data.getSound()));
            //receivePushTv.setText(data.getStoreCount());
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TO_CHANGE_MERCHANT_PUSH && resultCode == RESULT_OK) {
            initData();
        }
    }
}
