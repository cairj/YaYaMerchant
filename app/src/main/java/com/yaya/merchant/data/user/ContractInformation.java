package com.yaya.merchant.data.user;

import java.io.Serializable;

/**
 * Created by admin on 2018/9/1.
 */

public class ContractInformation implements Serializable {

    private String shop_platform_commission_rate;
    private String shop_offline_commission_rate;

    public String getShopPlatformCommissionRate() {
        return shop_platform_commission_rate;
    }

    public String getShopOfflineCommissionRate() {
        return shop_offline_commission_rate;
    }
}
