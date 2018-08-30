package com.yaya.merchant.data.user;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/17.
 */

public class BindInfo implements Serializable {

    private String shop_company_name;//公司名字",
    private String license_number;//营业执照",
    private String company_fuze;//负责人(登入账号为商家时的字段)",
    private String company_faren;//负责人(登入账号为代理商时的字段)",

    public String getShopCompanyName() {
        return shop_company_name;
    }

    public String getLicenseNumber() {
        return license_number;
    }

    public String getCompanyFuze() {
        return company_fuze;
    }

    public String getCompanyFaren() {
        return company_faren;
    }
}
