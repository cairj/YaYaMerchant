package com.yaya.merchant.widgets.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yaya.merchant.R;
import com.yaya.merchant.util.DateUtils;
import com.yaya.merchant.widgets.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.yaya.merchant.widgets.aigestudio.wheelpicker.view.WheelCurvedPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by admin on 2018/3/25.
 */

public class DatePickerPopupWindow extends PopupWindow {

    public static final String DEFAULT_VALUE = "----";

    private Context context;

    private View windowView;
    private TextView cancelTv;
    private TextView submitTv;
    private View externalView;

    private WheelCurvedPicker yearPicker;
    private WheelCurvedPicker monthPicker;
    private WheelCurvedPicker dayPicker;

    private ArrayList<String> years = new ArrayList<>();
    private ArrayList<String> months = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>();

    private int startYear = 1960, startMonth = 1, startDay = 1;
    private int endYear = 2020, endMonth = 12, endDay = 31;

    private int selectedYearIndex;
    private int selectedMonthIndex;
    private int selectedDayIndex;

    public DatePickerPopupWindow(Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        windowView = inflater.inflate(R.layout.popup_window_district_picker, null);

        this.setContentView(windowView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置窗体可点击
        setFocusable(true);
        setOutsideTouchable(true);
        //刷新状态
        update();
        //点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setAnimationStyle(R.style.Animation_AppCompat_DropDownUp);

        initView();
        initData();
        initListener();
    }


    private void initView() {
        cancelTv = (TextView) windowView.findViewById(R.id.cancel_tv);
        submitTv = (TextView) windowView.findViewById(R.id.submit_tv);
        externalView = windowView.findViewById(R.id.external_view);
        TextView titleTv = (TextView) windowView.findViewById(R.id.title_tv);
        titleTv.setText("选择日期");

        yearPicker = (WheelCurvedPicker) windowView.findViewById(R.id.province_picker);
        monthPicker = (WheelCurvedPicker) windowView.findViewById(R.id.city_picker);
        dayPicker = (WheelCurvedPicker) windowView.findViewById(R.id.district_picker);
    }

    private void initData() {
        //默认设置最后一天和默认选择的都是当天
        Calendar calendar = Calendar.getInstance();
        setDateRangeEnd(calendar.get(Calendar.YEAR), 12, 31);
        setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void initListener() {
        yearPicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {
            }

            @Override
            public void onWheelSelected(int index, String data) {
                selectedYearIndex = index;
                changeMonthData(index);
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });

        monthPicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {
            }

            @Override
            public void onWheelSelected(int index, String data) {
                selectedMonthIndex = index;
                changeDayData(Integer.parseInt(getSelectedYear()), Integer.parseInt(getSelectedMonth()));
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });

        dayPicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {
            }

            @Override
            public void onWheelSelected(int index, String data) {
                selectedDayIndex = index;
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSubmitTvClick(getSelectedYear(), getSelectedMonth(), getSelectedDay());
                    dismiss();
                }
            }
        });

        externalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show() {
        if (!this.isShowing()) {// 以下拉方式显示popupwindow
            this.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();
        }
    }

    private OnSubmitTvClickListener listener;

    public interface OnSubmitTvClickListener {
        void onSubmitTvClick(String year, String mouth, String day);
    }

    public void setOnSubmitTvClickListener(OnSubmitTvClickListener listener) {
        this.listener = listener;
    }

    /**
     * 设置范围：开始的年月日
     */
    public void setDateRangeStart(int startYear, int startMonth, int startDay) {
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
        initYearData();
    }

    /**
     * 设置范围：结束的年月日
     */
    public void setDateRangeEnd(int endYear, int endMonth, int endDay) {
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.endDay = endDay;
        initYearData();
    }

    /**
     * 设置默认选中的年月日时分
     */
    public void setSelectedItem(int year, int month, int day) {
        changeMonthData(year);
        changeDayData(year, month);
        selectedYearIndex = findItemIndex(years, year);
        selectedMonthIndex = findItemIndex(months, month);
        selectedDayIndex = findItemIndex(days, day);
        initYearData();
    }

    //折半查找有序元素的索引
    private int findItemIndex(ArrayList<String> items, int item) {
        int index = Collections.binarySearch(items, item, new Comparator<Object>() {
            @Override
            public int compare(Object lhs, Object rhs) {
                String lhsStr = lhs.toString();
                String rhsStr = rhs.toString();
                lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1) : lhsStr;
                rhsStr = rhsStr.startsWith("0") ? rhsStr.substring(1) : rhsStr;
                return Integer.parseInt(lhsStr) - Integer.parseInt(rhsStr);
            }
        });
        if (index < 0) {
            throw new IllegalArgumentException("Item[" + item + "] out of range");
        }
        return index;
    }

    //初始化年份
    private void initYearData() {
        years.clear();
        if (startYear == endYear) {
            years.add(String.valueOf(startYear));
        } else if (startYear < endYear) {
            //年份正序
            for (int i = startYear; i <= endYear; i++) {
                years.add(String.valueOf(i));
            }
        } else {
            //年份逆序
            for (int i = startYear; i >= endYear; i--) {
                years.add(String.valueOf(i));
            }
        }
        //刷新界面
        yearPicker.setData(years);
        yearPicker.setItemIndex(selectedYearIndex);
    }

    private void changeMonthData(int selectedYear) {
        String preSelectMonth;
        if (months.size() > selectedMonthIndex) {
            preSelectMonth = months.get(selectedMonthIndex);
        } else {
            preSelectMonth = DateUtils.fillZero(Calendar.getInstance().get(Calendar.MONTH) + 1);
        }
        months.clear();
        if (startMonth < 1 || endMonth < 1 || startMonth > 12 || endMonth > 12) {
            throw new IllegalArgumentException("Month out of range [1-12]");
        }
        if (startYear == endYear) {
            if (startMonth > endMonth) {
                for (int i = endMonth; i >= startMonth; i--) {
                    months.add(DateUtils.fillZero(i));
                }
            } else {
                for (int i = startMonth; i <= endMonth; i++) {
                    months.add(DateUtils.fillZero(i));
                }
            }
        } else if (selectedYear == startYear) {
            for (int i = startMonth; i <= 12; i++) {
                months.add(DateUtils.fillZero(i));
            }
        } else if (selectedYear == endYear) {
            for (int i = 1; i <= endMonth; i++) {
                months.add(DateUtils.fillZero(i));
            }
        } else {
            for (int i = 1; i <= 12; i++) {
                months.add(DateUtils.fillZero(i));
            }
        }
        //当前设置的月份不在指定范围，则默认选中范围开始的月份
        int preSelectMonthIndex = months.indexOf(preSelectMonth);
        selectedMonthIndex = preSelectMonthIndex == -1 ? 0 : preSelectMonthIndex;
        //刷新界面
        monthPicker.setData(months);
        monthPicker.setItemIndex(selectedMonthIndex);
    }

    private void changeDayData(int selectedYear, int selectedMonth) {
        int maxDays = DateUtils.calculateDaysInMonth(selectedYear, selectedMonth);
        if (selectedDayIndex >= maxDays) {
            //如果之前选择的日是之前年月的最大日，则日自动为该年月的最大日
            selectedDayIndex = maxDays - 1;
        }
        String preSelectDay;
        if (days.size() > selectedDayIndex) {
            //年或月变动时，保持之前选择的日不动
            preSelectDay = days.get(selectedDayIndex);
        } else {
            preSelectDay = DateUtils.fillZero(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        }
        days.clear();
        if (selectedYear == startYear && selectedMonth == startMonth
                && selectedYear == endYear && selectedMonth == endMonth) {
            //开始年月及结束年月相同情况
            for (int i = startDay; i <= endDay; i++) {
                days.add(DateUtils.fillZero(i));
            }
        } else if (selectedYear == startYear && selectedMonth == startMonth) {
            //开始年月相同情况
            for (int i = startDay; i <= maxDays; i++) {
                days.add(DateUtils.fillZero(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            //结束年月相同情况
            for (int i = 1; i <= endDay; i++) {
                days.add(DateUtils.fillZero(i));
            }
        } else {
            for (int i = 1; i <= maxDays; i++) {
                days.add(DateUtils.fillZero(i));
            }
        }
        //当前设置的日子不在指定范围，则默认选中范围开始的日子
        int preSelectDayIndex = days.indexOf(preSelectDay);
        selectedDayIndex = preSelectDayIndex == -1 ? 0 : preSelectDayIndex;
        //刷新界面
        dayPicker.setData(days);
        dayPicker.setItemIndex(selectedDayIndex);
    }

    //拼接日期
    public String getDate() {
        return getSelectedYear() + "-" + getSelectedMonth() + "-" + getSelectedDay();
    }

    //获取选择的年份
    public String getSelectedYear() {
        if (years.size() <= selectedYearIndex) {
            selectedYearIndex = years.size() - 1;
        }
        return years.get(selectedYearIndex);
    }

    //获取选择的月
    public String getSelectedMonth() {
        if (months.size() <= selectedMonthIndex) {
            selectedMonthIndex = months.size() - 1;
        }
        return months.get(selectedMonthIndex);
    }

    //获取选择的日
    public String getSelectedDay() {
        if (days.size() <= selectedDayIndex) {
            selectedDayIndex = days.size() - 1;
        }
        return days.get(selectedDayIndex);
    }


}
