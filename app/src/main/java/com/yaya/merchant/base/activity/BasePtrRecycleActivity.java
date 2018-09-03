package com.yaya.merchant.base.activity;

import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.util.Constants;
import com.yaya.merchant.widgets.GifPtrHeader;
import com.yaya.merchant.widgets.TipInfoLayout;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by 蔡蓉婕 on 2018/3/9.
 */

public abstract class BasePtrRecycleActivity<T extends Serializable> extends BaseActivity {

    protected PtrFrameLayout ptrFrame;
    protected RecyclerView recyclerView;
    protected TipInfoLayout tipInfo;

    protected List<T> mDataList = new ArrayList<>();
    protected BaseQuickAdapter adapter;
    protected LinearLayoutManager mLayoutManager;

    protected long mCurrentPos = Constants.DEFAULT_FIRST_PAGE_COUNT;
    protected int pageSize = 20;
    protected JsonResponse<BaseRowData<T>> mJsonResponse;

    protected boolean isLoading = false;
    protected boolean isFull = false;

    protected LayoutInflater layoutInflater;
    protected View footerView;
    protected TextView loadingTv;
    protected ProgressBar loadingProgress;


    @Override
    protected void initView() {
        super.initView();
        layoutInflater = LayoutInflater.from(this);
        findViews();
        initTipInfoEmptyView();
        initTipInfoErrorView();
        initFooterView();
        initRecycleView();
        initPtrFrame();
    }

    protected void findViews() {
        ptrFrame = (PtrFrameLayout) findViewById(R.id.ptr_frame);
        recyclerView = (RecyclerView)  findViewById(R.id.recycler_view);
        tipInfo = (TipInfoLayout) findViewById(R.id.tip_info);
    }

    @Override
    protected void initData() {
        super.initData();
        refresh();
    }

    protected void initRecycleView(){
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        addItemDecoration();

        adapter = getAdapter();
        adapter.addFooterView(footerView);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastItemPosition = mLayoutManager.findLastVisibleItemPosition();
                if (lastItemPosition+1 == adapter.getItemCount() && !isLoading && !isFull){
                    requestData();
                }
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.layout_base_ptr_recycle;
    }
    protected abstract BaseQuickAdapter getAdapter();
    protected abstract JsonResponse<BaseRowData<T>> getData() throws Exception;

    protected void addItemDecoration(){
        RecyclerView.ItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.gray_F6F7F9))
                .sizeResId(R.dimen.divider_height)
                .marginResId(R.dimen.margin_edge)
                .build();
        recyclerView.addItemDecoration(decoration);
    }

    /** 设置ptr */
    protected void initPtrFrame(){
        setPtrHandler();

        //设置下拉header
        GifPtrHeader header = new GifPtrHeader(this);
        ptrFrame.setHeaderView(header);
        ptrFrame.addPtrUIHandler(header);
    }

    //设置下拉事件
    protected void setPtrHandler(){
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    //包含在CoordinatorLayout内的fragment需要重写该事件
    protected boolean checkPtrCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    public void refresh() {
        mCurrentPos = Constants.DEFAULT_FIRST_PAGE_COUNT;
        isFull = false;
        requestData();
    }

    protected void requestData(){
        if (isLoading || isFull){
            return;
        }
        new AsyncTask<Void, Void, JsonResponse<BaseRowData<T>>>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                isLoading = true;
                setFooterLoading();
            }

            @Override
            protected JsonResponse<BaseRowData<T>> doInBackground(Void... params) {
                try {
                    return getData();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JsonResponse<BaseRowData<T>> ts) {
                super.onPostExecute(ts);
                if (ptrFrame != null){
                    ptrFrame.post(new Runnable() {
                        @Override
                        public void run() {
                            ptrFrame.refreshComplete();
                        }
                    });
                }
                if (ts == null || ts.getCode() != Constants.RESPONSE_SUCCESS){
                    isLoading = false;
                    onLoadFailed();
                }else {
                    mJsonResponse = ts;
                    onLoadJsonResponse(ts);
                }
            }
        }.execute();
    }

    protected void onLoadJsonResponse(JsonResponse<BaseRowData<T>> ts){
        onLoadSucceed(ts.getResultData().getRows());
    }

    protected void onLoadFailed(){
        tipInfo.showNetErrorContainer();
    }

    protected void onLoadSucceed(List<T> data){
        if (adapter.getItemCount() == 0 && mDataList.isEmpty()){
            tipInfo.showEmptyViewContainer();
        }else {
            tipInfo.setVisibility(View.GONE);
        }

        //如果是刷新，则清空列表
        if (mCurrentPos == Constants.DEFAULT_FIRST_PAGE_COUNT){
            this.mDataList.clear();
        }
        setData(data);

        mCurrentPos++;
        isLoading = false;
        judgeIsEmpty();

        if (isFull()) {
            isFull = true;
            isLoading = false;
            setFootFull();
        } else {
            isFull = false;
        }
    }

    protected boolean isFull(){
        return mJsonResponse.getResultData().getPageCount(pageSize) < mCurrentPos;
    }

    protected void judgeIsEmpty() {
        if (!isDataListEmpty()) {
            onDataNoEmpty();
        } else {
            onDataEmpty();
        }
    }

    protected void onDataNoEmpty(){
        tipInfo.setHiden();
    }

    protected void onDataEmpty(){//列表空数据时
        tipInfo.setVisibility(View.VISIBLE);
        tipInfo.showEmptyViewContainer();
    }

    protected void setData(List<T> data){
        this.mDataList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    //判断整个列表是否为空，有些界面虽然mDataList为空，但是添加了header
    protected boolean isDataListEmpty(){
        if (adapter instanceof BaseQuickAdapter){
            return ((BaseQuickAdapter)adapter).getHeaderLayoutCount() == 0 && mDataList.isEmpty();
        }else {
            return mDataList.isEmpty();
        }
    }

    protected void initTipInfoEmptyView() {
        View view = layoutInflater.inflate(R.layout.layout_member_feed_empty, null);
        ImageView emptyImg = (ImageView) view.findViewById(R.id.empty_img);
        TextView emptyHintTv = (TextView)view.findViewById(R.id.hint_tv);
        emptyImg.setImageResource(getEmptyViewImgId());
        emptyHintTv.setText(getEmptyViewHint());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        tipInfo.setEmptyViewContainer(view);
    }

    protected void initTipInfoErrorView() {
        View view = layoutInflater.inflate(R.layout.layout_member_feed_empty, null);
        ImageView emptyImg = (ImageView) view.findViewById(R.id.empty_img);
        TextView emptyHintTv = (TextView)view.findViewById(R.id.hint_tv);
        emptyImg.setImageResource(getEmptyViewImgId());
        emptyHintTv.setText(getEmptyViewHint());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        tipInfo.setEmptyViewContainer(view);
    }

    protected String getEmptyViewHint() {
        return getString(R.string.hint_empty_data);
    }
    protected int getEmptyViewImgId() {
        return R.mipmap.ic_empty;
    }

    public PtrFrameLayout getPtrFrame(){
        return ptrFrame;
    }

    //** 设置底部 *//*
    protected void initFooterView() {
        footerView = layoutInflater.inflate(R.layout.footer_recycle_view, null);
        loadingProgress = (ProgressBar) footerView.findViewById(R.id.loading_progress);
        loadingTv = (TextView) footerView.findViewById(R.id.loading_tv);
    }

    /** 设置footerView为加载中*/
    protected void setFooterLoading() {
        if (footerView != null){
            loadingTv.setText("加载中");
            loadingProgress.setVisibility(View.VISIBLE);
        }
    }

    /**设置footerView为已加载全部*/
    protected void setFootFull() {
        if (footerView != null){
            loadingTv.setText("已加载全部");
            loadingProgress.setVisibility(View.GONE);
        }
        isFull = true;
    }
}
