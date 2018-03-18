package com.yaya.merchant.widgets.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.account.Member;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import java.util.List;

/**
 * Created by admin on 2018/3/14.
 */

public class MemberManagerAdapter extends BaseQuickAdapter<Member> {
    public MemberManagerAdapter(List<Member> data) {
        super(R.layout.item_balance_account_child, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Member member) {
        baseViewHolder.setText(R.id.tv_pay_type,member.getMobile())
                .setText(R.id.tv_pay_time,member.getName())
                .setText(R.id.tv_pay_status,"已绑定")
                .setText(R.id.tv_pay_money,"");
        ImageView weixinIv = baseViewHolder.getView(R.id.iv_weixin);
        GlideLoaderHelper.loadAvatar(member.getHeadImgUrl(), (ImageView) baseViewHolder.getView(R.id.item_iv_head));
        //绑定方式
        TextView bindWay=baseViewHolder.getView(R.id.tv_pay_status);
        switch (member.getBindName()){
            case Member.BIND_WE_CHAT:
                bindWay.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_weixin,0);
                weixinIv.setVisibility(View.GONE);
                break;
            case Member.BIND_ALI_PAY:
                bindWay.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_zhifubao,0);
                weixinIv.setVisibility(View.GONE);
                break;
            case Member.BIND_WE_CHAT_AND_ALI_PAY:
                bindWay.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_zhifubao,0);
                weixinIv.setVisibility(View.VISIBLE);
                break;
        }

    }
}
