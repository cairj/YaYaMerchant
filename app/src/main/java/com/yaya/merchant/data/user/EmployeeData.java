package com.yaya.merchant.data.user;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/16.
 */

public class EmployeeData implements Serializable {

    private String id;
    private String logo;
    private String name;//员工名称
    private String roleName;//角色名称
    private String userName;//账号

    public String getId() {
        return id;
    }

    public String getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getUserName() {
        return userName;
    }
}
