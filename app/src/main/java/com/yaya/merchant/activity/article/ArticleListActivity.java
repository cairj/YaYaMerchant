package com.yaya.merchant.activity.article;

import com.yaya.merchant.R;
import com.yaya.merchant.base.activity.BaseTabLayoutActivity;
import com.yaya.merchant.data.Article;
import com.yaya.merchant.fragment.ArticleListFragment;
import com.yaya.merchant.util.StatusBarUtil;

/**
 * Created by admin on 2018/3/31.
 */

public class ArticleListActivity extends BaseTabLayoutActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_article_list;
    }

    @Override
    protected void initData() {
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        tabTitleList.add("通知");
        fragmentList.add(ArticleListFragment.getInstance(Article.TYPE_NOTIFY));

        tabTitleList.add("资讯");
        fragmentList.add(ArticleListFragment.getInstance(Article.TYPE_INFORMATION));

        adapter.notifyDataSetChanged();
    }
}
