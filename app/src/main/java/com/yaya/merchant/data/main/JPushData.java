package com.yaya.merchant.data.main;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/18.
 */

public class JPushData implements Serializable {
    private String id;//2,别名
    private String tenantId;//1，tag

    public String getId() {
        return id;
    }

    public String getTenantId() {
        return tenantId;
    }
}
