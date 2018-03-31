package com.yaya.merchant.widgets.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaya.merchant.R;
import com.yaya.merchant.data.Article;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import java.util.List;

/**
 * Created by admin on 2018/3/31.
 */

public class ArticleListAdapter extends BaseQuickAdapter<Article> {
    public ArticleListAdapter(List<Article> data) {
        super(R.layout.item_article,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Article article) {
        GlideLoaderHelper.loadImg(article.getImgUrl(), (ImageView) baseViewHolder.getView(R.id.iv_pic));

        baseViewHolder.setText(R.id.tv_title,article.getTitle())
                .setText(R.id.tv_desciption,article.getDesciption())
                .setText(R.id.tv_time,article.getCreationTime());
    }
}
