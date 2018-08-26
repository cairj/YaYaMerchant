package com.yaya.merchant.data.login;

import java.io.Serializable;

/**
 * Created by admin on 2018/8/25.
 */

public class TokenData implements Serializable {

    private String token;
    private String tel;

    public String getToken() {
        return token;
    }

    public String getTel() {
        return tel;
    }
}
