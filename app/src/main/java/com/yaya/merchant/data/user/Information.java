package com.yaya.merchant.data.user;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/18.
 */

public class Information implements Serializable {

    private String headImg;//头像
    private String corporation;//用户名
    private String phone;//手机
    private String name;//公司名
    private String admissibleBusiness;//所属代理商
    private String creationTime;//创建时间

    public String getHeadImg() {
        return headImg;
    }

    public String getCorporation() {
        return corporation;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getAdmissibleBusiness() {
        return admissibleBusiness;
    }

    public String getCreationTime() {
        return creationTime;
    }
}
