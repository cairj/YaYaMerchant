package com.yaya.merchant.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.activity.article.ArticleDetailActivity;
import com.yaya.merchant.base.fragment.BasePtrRecycleFragment;
import com.yaya.merchant.data.Article;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.widgets.adapter.ArticleListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by admin on 2018/3/31.
 */

public class ArticleListFragment extends BasePtrRecycleFragment<Article> {

    private String articleType;

    public static ArticleListFragment getInstance(String articleType) {
        ArticleListFragment articleListFragment = new ArticleListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("articleType", articleType);
        articleListFragment.setArguments(bundle);
        return articleListFragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.layout_base_ptr_recycle;
    }

    @Override
    protected void initView() {
        super.initView();
        ptrFrame.setPadding(DpPxUtil.dp2px(13), DpPxUtil.dp2px(10),
                DpPxUtil.dp2px(13), 0);
    }

    @Override
    protected void initData() {
        articleType = getArguments().getString("articleType");
        super.initData();
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        ArticleListAdapter adapter = new ArticleListAdapter(mDataList);
        adapter.setListener(new ArticleListAdapter.OnItemClickListener() {
            @Override
            public void onClick(Article article) {
                String title = articleType.equals(Article.TYPE_NOTIFY) ? "通知" : "咨询";
                ArticleDetailActivity.open(getActivity(), article.getId(), title);
            }
        });
        return adapter;
    }

    @Override
    protected JsonResponse<BaseRowData<Article>> getData() throws Exception {
        return MainDataAction.getArticleList(articleType, String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    protected void addItemDecoration() {
        RecyclerView.ItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(ContextCompat.getColor(getActivity(), R.color.gray_F6F7F9))
                .size(DpPxUtil.dp2px(10))
                .build();
        recyclerView.addItemDecoration(decoration);
    }
}
