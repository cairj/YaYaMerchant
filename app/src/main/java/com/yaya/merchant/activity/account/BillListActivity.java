package com.yaya.merchant.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.account.BillData;
import com.yaya.merchant.data.account.BillListData;
import com.yaya.merchant.util.Constants;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.widgets.adapter.MerchantBillGroupAdapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/24.
 */

public class BillListActivity extends BasePtrRecycleActivity<BillData> {

    private int billType = 0;
    public static final int DAILY_BILL = 1;
    public static final int WEEK_BILL = 2;
    public static final int MONTH_BILL = 3;

    private String startTime;
    private String endTime;
    private Calendar startCalendar;
    private Calendar endCalendar;

    private BillListData billListData;

    protected List<String> groupList = new ArrayList<>();
    protected TreeMap<String, List<BillData>> map = new TreeMap<>();

    @BindView(R.id.tv_real_amount)
    protected TextView realAmountTv;
    @BindView(R.id.tv_order_total)
    protected TextView orderTotalTv;
    @BindView(R.id.tv_date)
    protected TextView dateTv;
    @BindView(R.id.tv_last)
    protected TextView lastTv;
    @BindView(R.id.tv_next)
    protected TextView nextTv;

    public static void open(Context context, int billType) {
        Intent intent = new Intent(context, BillListActivity.class);
        intent.putExtra("billType", billType);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bill_list;
    }

    @Override
    protected void initData() {
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        billType = getIntent().getIntExtra("billType", 0);
        initCalendar();
        initDate();
        super.initData();
    }

    private void initCalendar() {
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        switch (billType) {
            case DAILY_BILL:
                lastTv.setText("上一天");
                nextTv.setText("下一天");
                setActionBarTitle("日账单");
                break;
            case WEEK_BILL:
                lastTv.setText("上一周");
                nextTv.setText("下一周");
                startCalendar.setFirstDayOfWeek(Calendar.MONDAY);
                endCalendar.setFirstDayOfWeek(Calendar.MONDAY);
                startCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                endCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                setActionBarTitle("周账单");
                break;
            case MONTH_BILL:
                lastTv.setText("上一月");
                nextTv.setText("下一月");
                startCalendar.set(Calendar.DAY_OF_MONTH, 1);
                endCalendar.set(startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH) + 1, 0);
                setActionBarTitle("月账单");
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
    }

    private void initDate() {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        if (billType == DAILY_BILL) {
            dateTv.setText(sp.format(startCalendar.getTime()));
        } else {
            dateTv.setText(sp.format(startCalendar.getTime()) + "\n" + sp.format(endCalendar.getTime()));
        }
    }

    @OnClick({R.id.tv_last, R.id.tv_next})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_last:
                setCalendar(-1);
                break;
            case R.id.tv_next:
                setCalendar(1);
                break;
        }
    }

    private void setCalendar(int num) {
        switch (billType) {
            case DAILY_BILL:
                startCalendar.set(Calendar.DAY_OF_MONTH, startCalendar.get(Calendar.DAY_OF_MONTH) + num);
                endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.get(Calendar.DAY_OF_MONTH) + num);
                break;
            case WEEK_BILL:
                startCalendar.set(Calendar.WEEK_OF_YEAR, startCalendar.get(Calendar.WEEK_OF_YEAR) + num);
                endCalendar.set(Calendar.WEEK_OF_YEAR, endCalendar.get(Calendar.WEEK_OF_YEAR) + num);
                break;
            case MONTH_BILL:
                startCalendar.set(Calendar.MONTH, startCalendar.get(Calendar.MONTH) + num);
                endCalendar.set(startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH) + 1, 0);
                break;
        }
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startTime = sp.format(startCalendar.getTime());
        endTime = sp.format(endCalendar.getTime());
        refresh();
        initDate();
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MerchantBillGroupAdapter(groupList);
    }

    @Override
    protected JsonResponse<BaseRowData<BillData>> getData() throws Exception {
        return null;
    }

    protected void requestData() {
        if (isLoading || isFull) {
            return;
        }
        new AsyncTask<Void, Void, JsonResponse<BillListData>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                isLoading = true;
                setFooterLoading();
            }

            @Override
            protected JsonResponse<BillListData> doInBackground(Void... params) {
                try {
                    return getBillList();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JsonResponse<BillListData> ts) {
                super.onPostExecute(ts);
                if (ptrFrame != null) {
                    ptrFrame.post(new Runnable() {
                        @Override
                        public void run() {
                            ptrFrame.refreshComplete();
                        }
                    });
                }
                if (ts == null || !ts.getData().isStatus()) {
                    isLoading = false;
                    onLoadFailed();
                } else {
                    loadJsonResponse(ts);
                }
            }
        }.execute();
    }

    private JsonResponse<BillListData> getBillList() throws IOException {
        return MainDataAction.getBillList(startTime, endTime, String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    protected void loadJsonResponse(JsonResponse<BillListData> ts) {
        billListData = ts.getData().getData();
        if (mCurrentPos == Constants.DEFAULT_FIRST_PAGE_COUNT) {
            realAmountTv.setText(billListData.getRealAmount());
            orderTotalTv.setText(billListData.getOrdertotal());
        }
        onLoadSucceed(billListData.getRows());
    }

    @Override
    protected void setData(List<BillData> dataList) {
        if (mCurrentPos == Constants.DEFAULT_FIRST_PAGE_COUNT) {
            map.clear();
        }
        for (BillData data : dataList) {
            String[] payTime = data.getPayTime().split("T");
            if (map.containsKey(payTime[0])) {
                map.get(payTime[0]).add(data);
            } else {
                List<BillData> balanceList = new ArrayList<>();
                balanceList.add(data);
                map.put(payTime[0], balanceList);
            }
        }
        Iterator<String> iterator = map.descendingKeySet().iterator();
        groupList.clear();
        while (iterator.hasNext()) {
            groupList.add(iterator.next());
        }
        ((MerchantBillGroupAdapter) adapter).setBalanceHashMap(map);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected boolean isFull() {
        return billListData.getPageCount(pageSize) < mCurrentPos;
    }
}
