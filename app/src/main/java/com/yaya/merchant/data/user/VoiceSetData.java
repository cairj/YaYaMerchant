package com.yaya.merchant.data.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/18.
 */

public class VoiceSetData implements Serializable {

    @SerializedName("voice")
    private String isVoice;//总开关 1开 else不开启
    private String sound;//语音设置 默认为开值为1",
    private String letter;//消息推送设置 默认为开值为1
    private String storeCount;//全部店铺
    private String audition;//试听内容

    public String getIsVoice() {
        return isVoice;
    }

    public String getSound() {
        return sound;
    }

    public String getLetter() {
        return letter;
    }

    public String getStoreCount() {
        return storeCount;
    }

    public String getAudition() {
        return audition;
    }
}
