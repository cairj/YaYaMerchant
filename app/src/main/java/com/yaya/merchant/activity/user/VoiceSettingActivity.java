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

    private String auditionMessage;//试听内容

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
        UserAction.getVoiceSet(new GsonCallback<VoiceSetData>(VoiceSetData.class) {
            @Override
            public void onSucceed(JsonResponse<VoiceSetData> response) {
                VoiceSetData data = response.getData().getData();
                auditionMessage = data.getAudition();
                voiceAlertTgBtn.setChecked("1".equals(data.getIsVoice()));
                initAlertWay(data.getVoiceType() == null ? "" : data.getVoiceType());
                receivePushTv.setText(data.getStoreCount());
            }
        });
    }

    //设置提醒模式
    private void initAlertWay(String way) {
        switch (way) {
            case "":
                alertWayPushTgBtn.setChecked(false);
                alertWayVoiceTgBtn.setChecked(false);
                break;
            case "1":
                alertWayPushTgBtn.setChecked(false);
                alertWayVoiceTgBtn.setChecked(true);
                break;
            case "2":
                alertWayPushTgBtn.setChecked(true);
                alertWayVoiceTgBtn.setChecked(false);
                break;
            case "3":
                alertWayPushTgBtn.setChecked(true);
                alertWayVoiceTgBtn.setChecked(true);
                break;
        }
    }

    @OnClick({R.id.tg_btn_voice_alert, R.id.tg_btn_alert_way_voice,
            R.id.tg_btn_alert_way_push, R.id.fl_receive_push_merchant, R.id.tv_audition})
    protected void onclick(View view) {
        switch (view.getId()) {
            case R.id.tg_btn_voice_alert:
            case R.id.tg_btn_alert_way_voice:
            case R.id.tg_btn_alert_way_push:
                toggleVoiceAlert();
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

    private void toggleVoiceAlert() {
        String voiceAlert = voiceAlertTgBtn.isChecked() ? "1" : "0";
        String alertWay = "";
        if (alertWayVoiceTgBtn.isChecked()) {//有语音
            alertWay = alertWayPushTgBtn.isChecked() ? "3"/*且有推送*/ : "1"/*只有语音*/;
        } else {//没语音
            alertWay = alertWayPushTgBtn.isChecked() ? "2"/*只有推送*/ : ""/*都没有*/;
        }
        UserAction.setVoice(voiceAlert, alertWay, new GsonCallback<String>(String.class) {
            @Override
            public void onSucceed(JsonResponse<String> response) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TO_CHANGE_MERCHANT_PUSH && resultCode == RESULT_OK) {
            initData();
        }
    }
}
