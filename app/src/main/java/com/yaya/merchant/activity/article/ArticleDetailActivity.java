package com.yaya.merchant.activity.article;

import android.content.Context;
import android.content.Intent;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseWebViewActivity;
import com.yaya.merchant.net.UrlH5s;
import com.yaya.merchant.util.StatusBarUtil;

/**
 * 通知、咨询详情
 */

public class ArticleDetailActivity extends BaseWebViewActivity {

    private String id;
    private String title;

    public static void open(Context context,String id,String title){
        Intent intent = new Intent(context,ArticleDetailActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        title = getIntent().getStringExtra("title");
        setActionBarTitle(title+"详情");
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        url = UrlH5s.getArticleDetail(id);
        super.initData();
    }
}
