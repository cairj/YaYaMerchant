package com.yaya.merchant.data.user;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/18.
 */

public class VoiceSetData implements Serializable {

    private String isVoice;//是否开启语音提示 1开 else不开启
    private String voiceType;//null 都不选，1语音，2消息推送 3 都选
    private String storeCount;//全部店铺
    private String audition;//试听内容

    public String getIsVoice() {
        return isVoice;
    }

    public String getVoiceType() {
        return voiceType;
    }

    public String getStoreCount() {
        return storeCount;
    }

    public String getAudition() {
        return audition;
    }
}
