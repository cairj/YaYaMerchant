package com.yaya.merchant.fragment.goods;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.GoodsAction;
import com.yaya.merchant.activity.good.GoodDetailActivity;
import com.yaya.merchant.base.fragment.BasePtrRecycleFragment;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.data.goods.Goods;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.DeviceParamsUtil;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.widgets.adapter.GoodsAdapter;
import com.yaya.merchant.widgets.adapter.SingleChoiceTextAdapter;
import com.yaya.merchant.widgets.decoration.GridItemDecoration;
import com.yaya.merchant.widgets.popupwindow.SingleChoiceWindow;
import com.yaya.merchant.widgets.popupwindow.screenwindow.TimePickerWindow;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 蔡蓉婕 on 2018/9/5.
 */

public class GoodsFragment extends BasePtrRecycleFragment<Goods> {

    private String keyword;
    private String state;
    private String startTime;
    private String endTime;

    @BindView(R.id.tv_change_status)
    protected TextView changeStatusTv;
    @BindView(R.id.tv_screen)
    protected TextView screenTv;

    @BindView(R.id.tv_title)
    protected TextView titleTv;

    @BindView(R.id.tv_status_bar)
    protected View statusBarView;

    protected SingleChoiceWindow singleChoiceWindow;
    protected SingleChoiceTextAdapter singleChoiceAdapter;
    protected ArrayList<ChoiceItem> singleChoiceItemList = new ArrayList<>();

    private TimePickerWindow timePickerWindow;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void initView() {
        super.initView();
        initSingleChoiceWindow();
        initTimePickerWindow();

        titleTv.setText("商品");

        ViewGroup.LayoutParams lp = statusBarView.getLayoutParams();
        lp.height = DeviceParamsUtil.getStatusBarHeight(getActivity());
        statusBarView.setLayoutParams(lp);
    }

    protected void initSingleChoiceWindow(){
        singleChoiceWindow=new SingleChoiceWindow(getActivity());
        singleChoiceAdapter = new SingleChoiceTextAdapter(singleChoiceItemList);
        singleChoiceWindow.setAdapter(singleChoiceAdapter);
        singleChoiceWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeStatusTv.setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_down,0);
            }
        });

        for (int i = 0 ;i<Goods.STATUS.length;i++){
            singleChoiceItemList.add(new ChoiceItem(Goods.STATUS[i],Goods.STATUS_PARAMS[i]));
        }
        singleChoiceItemList.add(0,new ChoiceItem("全部状态",""));
        singleChoiceItemList.get(0).setSelect(true);
        singleChoiceAdapter.notifyDataSetChanged();

        singleChoiceAdapter.setListener(new SingleChoiceTextAdapter.OnItemClickListener() {
            @Override
            public void onClick(ChoiceItem item) {
                for (ChoiceItem choiceItem : singleChoiceItemList) {
                    choiceItem.setSelect(false);
                }
                item.setSelect(true);
                singleChoiceAdapter.notifyDataSetChanged();
                changeStatusTv.setText(item.getContent());
                state = item.getId();
                refresh();
                singleChoiceWindow.dismiss();
            }
        });
   }

    private void initTimePickerWindow() {
        timePickerWindow = new TimePickerWindow(getActivity()){
            @Override
            protected void initAdapter() {
                super.initAdapter();
                choiceItemList.add(new ChoiceItem("最近30天","3"));
                choiceItemList.add(new ChoiceItem("最近三个月","4"));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onItemClick(ChoiceItem item) {
                if (item.getId().equals("3")){
                    initCalendar();
                    startCalendar.set(Calendar.DATE,endCalendar.get(Calendar.DATE)-30);
                    initPickerText();
                }else if (item.getId().equals("4")){
                    initCalendar();
                    startCalendar.set(Calendar.DATE,endCalendar.get(Calendar.DATE)-90);
                    initPickerText();
                }
                super.onItemClick(item);
            }

            @Override
            protected void initView() {
                super.initView();
                startDataLL.setVisibility(View.GONE);
                endDataLL.setVisibility(View.GONE);
                timeTitleTv.setVisibility(View.GONE);
                choiceItemListRv.setLayoutManager(new GridLayoutManager(getActivity(),2));
                phoneTitleTv.setText("关键字搜索");
            }

            @Override
            protected RecyclerView.ItemDecoration addItemDecoration() {
                RecyclerView.ItemDecoration itemDecoration = new GridItemDecoration(DpPxUtil.dp2px(20), DpPxUtil.dp2px(14));
                return itemDecoration;
            }

            @Override
            protected void reset() {
                super.reset();
            }
        };
        timePickerWindow.setHintTvVisible(true);
        timePickerWindow.setPhoneLLVisible(true);
        timePickerWindow.setListener(new TimePickerWindow.OnSubmitListener() {
            @Override
            public void submit(String startTime, String endTime, String phone) {
                GoodsFragment.this.startTime = startTime;
                GoodsFragment.this.endTime = endTime;
                keyword = phone;
                refresh();
            }
        });
    }

    @OnClick({R.id.tv_change_status,R.id.tv_screen})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.tv_change_status:
                singleChoiceWindow.showDropDown(changeStatusTv);
                changeStatusTv.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_up,0);
                break;
            case R.id.tv_screen:
                timePickerWindow.showDropDown(changeStatusTv);
                break;
        }
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        final GoodsAdapter adapter = new GoodsAdapter(mDataList);
        adapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemBtnClick(final Goods goods) {
                GsonCallback<Goods> callback = new GsonCallback<Goods>(Goods.class) {
                    @Override
                    public void onSucceed(JsonResponse<Goods> response) {
                        goods.setApplyState(response.getResultData().getApplyState());
                        goods.setState(response.getResultData().getState());
                        adapter.notifyDataSetChanged();
                    }
                };
                if (Goods.STATUS_SOLD_OUT.equals(goods.getState()) && Goods.APPLY_STATE_NOT_APPLY.equals(goods.getApplyState())) {
                    GoodsAction.changeGoodState(Goods.STATUS_SOLD_OUT,goods.getGoodsId(),callback);
                }

                if (Goods.STATUS_PUT_AWAY.equals(goods.getState())) {
                    GoodsAction.changeGoodState(Goods.STATUS_PUT_AWAY,goods.getGoodsId(),callback);
                }

                if (Goods.STATUS_SOLD_OUT.equals(goods.getState()) && Goods.APPLY_STATE_APPLYING.equals(goods.getApplyState())) {
                    GoodsAction.changeGoodState(Goods.STATUS_APPLYING,goods.getGoodsId(),callback);
                }
            }

            @Override
            public void onItemClick(Goods goods) {
                GoodDetailActivity.open(getActivity(),goods.getGoodsId());
            }
        });
        return adapter;
    }

    @Override
    protected JsonResponse<BaseRowData<Goods>> getData() throws Exception {
        return GoodsAction.queryGoodsList(keyword, state, startTime, endTime, String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }
}
