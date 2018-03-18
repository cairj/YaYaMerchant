package com.yaya.merchant.widgets.adapter;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.user.EmployeeData;
import com.yaya.merchant.data.user.MerchantData;
import com.yaya.merchant.util.TextFontUtil;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import java.util.List;

/**
 * Created by 蔡蓉婕 on 2018/3/16.
 */

public class EmployeeManagerAdapter extends BaseQuickAdapter<EmployeeData> {
    public EmployeeManagerAdapter(List<EmployeeData> data) {
        super(R.layout.item_merchant_manager, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, EmployeeData data) {
        baseViewHolder.setText(R.id.tv_merchant_address, "账号：" + data.getUserName());

        GlideLoaderHelper.loadAvatar(data.getLogo(), (ImageView) baseViewHolder.getView(R.id.iv_logo));

        TextView nameTv = baseViewHolder.getView(R.id.tv_merchant_name);
        String content = data.getName() + "(" + data.getRoleName() + ")";
        TextFontUtil.setTextColor(nameTv, content,
                Color.RED, content.lastIndexOf("("), content.length());

    }
}
