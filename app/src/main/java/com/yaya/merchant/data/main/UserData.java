package com.yaya.merchant.data.main;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/16.
 */

public class UserData implements Serializable {
    private String headImgUrl;//头像
    private String name;//管理员名称
    private String roleName;//角色名称
    private String storeCount;//门店数量
    private String userCount;//员工数量


    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public String getName() {
        return name;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getStoreCount() {
        return storeCount;
    }

    public String getUserCount() {
        return userCount;
    }
}
