package com.yaya.merchant.widgets.adapter;

import android.view.View;
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
        super(R.layout.item_article, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final Article article) {
        GlideLoaderHelper.loadImg(article.getImgUrl(), (ImageView) baseViewHolder.getView(R.id.iv_pic));

        baseViewHolder.setText(R.id.tv_title, article.getTitle())
                .setText(R.id.tv_desciption, article.getDesciption())
                .setText(R.id.tv_time, article.getCreationTime());

        baseViewHolder.getView(R.id.fl_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(article);
                }
            }
        });
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(Article article);
    }
}
