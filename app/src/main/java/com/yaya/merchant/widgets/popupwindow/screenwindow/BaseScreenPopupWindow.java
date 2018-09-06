package com.yaya.merchant.widgets.popupwindow.screenwindow;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yaya.merchant.R;
import com.yaya.merchant.base.BasePopupWindow;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.widgets.adapter.ScreenWindowAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 筛选
 */

public class BaseScreenPopupWindow extends BasePopupWindow {

    protected RecyclerView choiceItemListRv;
    protected BaseQuickAdapter mAdapter;

    protected DatePickerDialog startDatePickerDialog;
    protected TimePickerDialog startTimePickerDialog;
    protected Calendar startCalendar;

    protected DatePickerDialog endDatePickerDialog;
    protected TimePickerDialog endTimePickerDialog;
    protected Calendar endCalendar;

    protected List<ChoiceItem> choiceItemList;
    protected int itemWidth = 106;

    @BindView(R.id.tv_start_date)
    protected TextView startDateTv;
    @BindView(R.id.tv_start_time)
    protected TextView startTimeTv;
    @BindView(R.id.tv_end_date)
    protected TextView endDateTv;
    @BindView(R.id.tv_end_time)
    protected TextView endTimeTv;

    public BaseScreenPopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.window_screen;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, windowView);
        choiceItemList = new ArrayList<>();
        choiceItemListRv = windowView.findViewById(R.id.rv_choose_items);
        choiceItemListRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        choiceItemListRv.addItemDecoration(addItemDecoration());
        initAdapter();
        initCalendar();
        initPickerDialog();
        initPickerText();
    }

    private void setTimeTvContent(TextView tv, Calendar calendar,String pattern) {
        SimpleDateFormat sp = new SimpleDateFormat(pattern);
        tv.setText(sp.format(calendar.getTime()));
    }

    @OnClick({R.id.tv_start_date, R.id.tv_end_date, R.id.tv_start_time, R.id.tv_end_time,R.id.tv_reset,R.id.tv_submit})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_date:
                startDatePickerDialog.show();
                break;
            case R.id.tv_end_date:
                endDatePickerDialog.show();
                break;
            case R.id.tv_start_time:
                startTimePickerDialog.show();
                break;
            case R.id.tv_end_time:
                endTimePickerDialog.show();
                break;
            case R.id.tv_reset:
                reset();
                break;
            case R.id.tv_submit:
                submit();
                break;

        }
    }

    protected void afterChooseDate(){
    }

    protected void reset(){
    }

    protected void submit(){
    }

    protected void initAdapter(){
        mAdapter = new ScreenWindowAdapter(choiceItemList,itemWidth);
        choiceItemListRv.setAdapter(mAdapter);
    }

    public <T> void setAdapter(BaseQuickAdapter<T> adapter) {
        mAdapter = adapter;
        choiceItemListRv.setAdapter(mAdapter);
    }

    protected RecyclerView.ItemDecoration addItemDecoration() {
        RecyclerView.ItemDecoration decoration = new VerticalDividerItemDecoration.Builder(context)
                .color(ContextCompat.getColor(context, R.color.transparent))
                .size(DpPxUtil.dp2px(11))
                .marginResId(R.dimen.margin_edge)
                .build();
        return decoration;
    }

    private void initPickerDialog() {
        startDatePickerDialog = new DatePickerDialog(context, new OnDatePickerChooseListener(true),
                startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DATE));
        startTimePickerDialog = new TimePickerDialog(context, new OnTimePickerChooseListener(true),
                startCalendar.get(Calendar.HOUR_OF_DAY), startCalendar.get(Calendar.MINUTE), true);

        endDatePickerDialog = new DatePickerDialog(context, new OnDatePickerChooseListener(false),
                startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DATE));
        endTimePickerDialog = new TimePickerDialog(context, new OnTimePickerChooseListener(false),
                startCalendar.get(Calendar.HOUR_OF_DAY), startCalendar.get(Calendar.MINUTE), true);
    }

    protected void initCalendar(){
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
    }

    protected void initPickerText(){
        setTimeTvContent(startDateTv,startCalendar,"yyyy-MM-dd");
        setTimeTvContent(endDateTv,endCalendar,"yyyy-MM-dd");
        setTimeTvContent(startTimeTv,startCalendar,"HH:mm");
        setTimeTvContent(endTimeTv,endCalendar,"HH:mm");
    }

    private class OnDatePickerChooseListener implements DatePickerDialog.OnDateSetListener {

        private boolean isStartTime;

        public OnDatePickerChooseListener(boolean isStartTime) {
            this.isStartTime = isStartTime;
        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (isStartTime) {
                startCalendar.set(year, month, dayOfMonth);
                setTimeTvContent(startDateTv,startCalendar,"yyyy-MM-dd");
            } else {
                endCalendar.set(year, month, dayOfMonth);
                setTimeTvContent(endDateTv,endCalendar,"yyyy-MM-dd");
            }
            afterChooseDate();
        }
    }

    private class OnTimePickerChooseListener implements TimePickerDialog.OnTimeSetListener {

        private boolean isStartTime;

        public OnTimePickerChooseListener(boolean isStartTime) {
            this.isStartTime = isStartTime;
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (isStartTime) {
                startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                startCalendar.set(Calendar.MINUTE, minute);
                setTimeTvContent(startTimeTv,startCalendar,"HH:mm");
            } else {
                endCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                endCalendar.set(Calendar.MINUTE, minute);
                setTimeTvContent(endTimeTv,endCalendar,"HH:mm");
            }
            afterChooseDate();
        }
    }

}
