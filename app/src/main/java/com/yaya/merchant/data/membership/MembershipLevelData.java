package com.yaya.merchant.data.membership;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 蔡蓉婕 on 2018/10/12.
 */

public class MembershipLevelData implements Serializable {

    @SerializedName("level_id")
    private String levelId;
    @SerializedName("level_name")
    private String levelName;

    public String getLevelId() {
        return levelId;
    }

    public String getLevelName() {
        return levelName;
    }
}
