package com.yaya.merchant.activity.order;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.OrderAction;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.order.OrderDetail;
import com.yaya.merchant.net.Urls;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.util.EventBusTags;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.widgets.adapter.OrderDetailAdapter;
import com.yaya.merchant.widgets.adapter.OrderListAdapter;
import com.yaya.merchant.widgets.popupwindow.DatePickerPopupWindow;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/25.
 */

public class OrderListActivity extends BasePtrRecycleActivity<OrderDetail> {

    private int isScan;
    private String startTime;
    private String endTime;
    private String orderStatus = "";

    private Calendar startCalendar;
    private Calendar endCalendar;

    @BindView(R.id.ll_choice)
    protected LinearLayout choiceLL;
    @BindView(R.id.tv_today)
    protected TextView todayTv;
    @BindView(R.id.tv_yesterday)
    protected TextView yesterdayTv;
    @BindView(R.id.tv_this_month)
    protected TextView thisMonthTv;
    @BindView(R.id.tv_custom)
    protected TextView customTv;
    @BindView(R.id.ll_choose_date)
    protected LinearLayout chooseDateLL;
    @BindView(R.id.tv_start_time)
    protected TextView startTimeTv;
    @BindView(R.id.tv_end_time)
    protected TextView endTimeTv;

    private DatePickerPopupWindow startTimePopupWindow;
    private DatePickerPopupWindow endTimePopupWindow;

    public static void open(Context context, int isScan) {
        Intent intent = new Intent(context, OrderListActivity.class);
        intent.putExtra("isScan", isScan);
        context.startActivity(intent);
    }

    public static void open(Context context, String orderStatus) {
        Intent intent = new Intent(context, OrderListActivity.class);
        intent.putExtra("orderStatus", orderStatus);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initView() {
        isScan = getIntent().getIntExtra("isScan", -1);
        orderStatus = getIntent().getStringExtra("orderStatus");
        super.initView();
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        if (OrderDetail.TYPE_DELIVER_ORDER_LIST.equals(orderStatus)){
            setActionBarTitle("发货");
            choiceLL.setVisibility(View.GONE);
        } else if (OrderDetail.TYPE_REFUND_ORDER_LIST.equals(orderStatus)){
            setActionBarTitle("退款");
            choiceLL.setVisibility(View.GONE);
        } else {
            setActionBarTitle("订单列表");
            choiceLL.setVisibility(View.VISIBLE);
        }
        startTimePopupWindow = new DatePickerPopupWindow(this);
        startTimePopupWindow.setOnSubmitTvClickListener(new DatePickerPopupWindow.OnSubmitTvClickListener() {
            @Override
            public void onSubmitTvClick(String year, String mouth, String day) {
                startTime = year + "-" + mouth + "-" + day + " 00:00:00";
                startTimeTv.setText(year + "-" + mouth + "-" + day);
                customTimeRefresh();
            }
        });
        endTimePopupWindow = new DatePickerPopupWindow(this);
        endTimePopupWindow.setOnSubmitTvClickListener(new DatePickerPopupWindow.OnSubmitTvClickListener() {
            @Override
            public void onSubmitTvClick(String year, String mouth, String day) {
                endTime = year + "-" + mouth + "-" + day + " 23:59:59";
                endTimeTv.setText(year + "-" + mouth + "-" + day);
                customTimeRefresh();
            }
        });
    }

    //自定义时间后进行刷新
    private void customTimeRefresh() {
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            if (endTime.compareTo(startTime) > 0) {
                refresh();
            }
        }
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        OrderListAdapter adapter = new OrderListAdapter(mDataList);
        adapter.setType(orderStatus);
        adapter.setListener(new OrderListAdapter.OnClickListener() {
            @Override
            public void onParentClick(OrderDetail orderData) {
                OrderDetailActivity.open(OrderListActivity.this,orderData,orderStatus);
            }

            @Override
            public void onBtnClick(OrderDetail orderDetail) {
                switch (orderStatus) {
                    case OrderDetail.TYPE_DELIVER_ORDER_LIST:
                        DeliverOrderActivity.open(OrderListActivity.this, orderDetail);
                        break;
                    case OrderDetail.TYPE_REFUND_ORDER_LIST:
                        RefundOrderActivity.open(OrderListActivity.this, orderDetail);
                        break;
                }
            }
        });
        return adapter;
    }

    @Override
    protected JsonResponse<BaseRowData<OrderDetail>> getData() throws Exception {
        String scan = isScan == -1 ? "" : String.valueOf(isScan);
        return OrderAction.getOrderList(startTime, endTime,scan,orderStatus,
                String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    protected void addItemDecoration() {
        RecyclerView.ItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.gray_F6F7F9))
                .size(DpPxUtil.dp2px(10))
                .build();
        recyclerView.addItemDecoration(decoration);
    }

    @OnClick({R.id.tv_custom, R.id.tv_start_time, R.id.tv_end_time})
    protected void custom(View view) {
        switch (view.getId()) {
            case R.id.tv_custom:
                if (chooseDateLL.getVisibility() != View.VISIBLE) {
                    startTime = "";
                    endTime = "";
                    startTimeTv.setText("");
                    endTimeTv.setText("");
                    chooseDateLL.setVisibility(View.VISIBLE);
                    todayTv.setSelected(false);
                    yesterdayTv.setSelected(false);
                    thisMonthTv.setSelected(false);
                    customTv.setSelected(true);
                }
                break;
            case R.id.tv_start_time:
                startTimePopupWindow.show();
                break;
            case R.id.tv_end_time:
                endTimePopupWindow.show();
                break;
        }
    }

    @OnClick({R.id.tv_today, R.id.tv_yesterday, R.id.tv_this_month})
    protected void onClick(View view) {
        chooseDateLL.setVisibility(View.GONE);
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        switch (view.getId()) {
            case R.id.tv_today:
                todayTv.setSelected(true);
                yesterdayTv.setSelected(false);
                thisMonthTv.setSelected(false);
                customTv.setSelected(false);
                break;
            case R.id.tv_yesterday:
                todayTv.setSelected(false);
                yesterdayTv.setSelected(true);
                thisMonthTv.setSelected(false);
                customTv.setSelected(false);
                startCalendar.set(Calendar.DAY_OF_MONTH, startCalendar.get(Calendar.DAY_OF_MONTH) - 1);
                endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.get(Calendar.DAY_OF_MONTH) - 1);
                break;
            case R.id.tv_this_month:
                todayTv.setSelected(false);
                yesterdayTv.setSelected(false);
                thisMonthTv.setSelected(true);
                customTv.setSelected(false);
                startCalendar.set(Calendar.DAY_OF_MONTH, 1);
                endCalendar.set(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH) + 1, 0);
                break;
        }
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startTime = sp.format(startCalendar.getTime());
        endTime = sp.format(endCalendar.getTime());
        refresh();
    }

    @Subscriber(tag= EventBusTags.DELIVER_ORDER_SUCCESS)
    private void deliverOrderSuccess(String str){
        refresh();
    }

}
