package com.yaya.merchant.data.user;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 蔡蓉婕 on 2018/9/7.
 */

public class MerchantReportForms implements Serializable {

    private List<String> day;
    private List<Float> nums;

    public List<String> getDay() {
        return day;
    }

    public List<Float> getNums() {
        return nums;
    }
}
