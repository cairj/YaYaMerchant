package com.yaya.merchant.fragment.report;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.ReportAction;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.data.user.MerchantReportForms;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.ChartUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 蔡蓉婕 on 2018/9/18.
 */

public class OrderReportFragment extends BaseFragment{

    @BindView(R.id.chart_line)
    LineChart dataChartLine;

    @BindView(R.id.tv_early_7_day)
    protected TextView early7DayTv;
    @BindView(R.id.tv_early_14_day)
    protected TextView early14DayTv;
    @BindView(R.id.tv_early_30_day)
    protected TextView early30DayTv;

    Calendar calendar;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_report;
    }

    @Override
    protected void initView() {
        super.initView();
        ChartUtils.initChart(dataChartLine);
        setChartYAxis();
        dataChartLine.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
        early7DayTv.setSelected(true);
    }

    @Override
    protected void initData() {
        super.initData();
        setCalendar(7);
        getOrderReport();
    }

    private void getOrderReport(){
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sp.format(calendar.getTime());
        ReportAction.getOrderReportData(startTime, new GsonCallback<MerchantReportForms>(MerchantReportForms.class) {
            @Override
            public void onSucceed(JsonResponse<MerchantReportForms> response) {
                List<Entry> entries = new ArrayList<>();
                MerchantReportForms data = response.getResultData();
                for (int i = 0; i < data.getNums().size(); i++) {
                    /**
                     * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
                     * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
                     */
                    Entry entry = new Entry(i, data.getNums().get(i));
                    entries.add(entry);
                }
                if (dataChartLine.getLineData() != null) {
                    dataChartLine.getLineData().clearValues();
                }
                ChartUtils.setChartData(dataChartLine, entries);
                ChartUtils.setXAxis(dataChartLine,data.getDay());
                //dataChartLine.zoom(data.getDay().size()/7,1,0,0);//一页只显示7条数据，其他可滑动显示
            }
        });
    }

    private void setCalendar(int day){
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - day);
    }

    private void setChartYAxis() {
        YAxis leftAxis = dataChartLine.getAxisLeft();
        //设置从Y轴发出横向直线(网格线)
        leftAxis.setDrawGridLines(true);
        //设置网格线的颜色
        leftAxis.setGridColor(ContextCompat.getColor(getActivity(),R.color.black_666666));
        //设置网格线宽度
        leftAxis.setGridLineWidth(1);
        //设置Y轴最小值是0，从0开始
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGranularityEnabled(true);
        //设置最小间隔，防止当放大时出现重复标签
        leftAxis.setGranularity(1f);
        //如果沿着轴线的线应该被绘制，则将其设置为true,隐藏Y轴
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setTextSize(10f);
        leftAxis.setTextColor(Color.BLACK);
        //设置左边X轴显示
        leftAxis.setEnabled(true);
        //设置Y轴的颜色
        leftAxis.setAxisLineColor(Color.BLACK);
        //设置Y轴的宽度
        leftAxis.setAxisLineWidth(1f);

        YAxis rightAxis = dataChartLine.getAxisRight();
        //设置右边Y轴不显示
        rightAxis.setEnabled(false);
    }

    @OnClick({R.id.tv_early_7_day,R.id.tv_early_14_day,R.id.tv_early_30_day})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.tv_early_7_day:
                setCalendar(7);
                getOrderReport();
                break;
            case R.id.tv_early_14_day:
                setCalendar(14);
                getOrderReport();
                break;
            case R.id.tv_early_30_day:
                setCalendar(30);
                getOrderReport();
                break;
        }
    }
}
