package com.yaya.merchant.widgets.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import java.util.List;

/**
 * Created by admin on 2018/3/21.
 */

public class VerificationResultAdapter extends BaseQuickAdapter<String> {
    public VerificationResultAdapter(List<String> data) {
        super(R.layout.item_verification_result, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        GlideLoaderHelper.loadImg(s, (ImageView) baseViewHolder.getView(R.id.iv_result));
    }
}
