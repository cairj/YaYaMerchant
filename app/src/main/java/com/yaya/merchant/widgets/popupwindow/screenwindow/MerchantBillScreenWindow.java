package com.yaya.merchant.widgets.popupwindow.screenwindow;

import android.content.Context;

import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.data.account.BillDetailData;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.widgets.adapter.ScreenWindowAdapter;
import com.yaya.merchant.widgets.adapter.SingleChoiceTextAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by admin on 2018/4/1.
 */

public class MerchantBillScreenWindow extends BaseScreenPopupWindow {

    public MerchantBillScreenWindow(Context context) {
        super(context);
    }

    @Override
    protected void initAdapter() {
        itemWidth = 75;
        super.initAdapter();
        for (int i = 0; i < BillDetailData.PAY_TYPE.length; i++) {
            choiceItemList.add(new ChoiceItem(BillDetailData.PAY_TYPE[i], BillDetailData.PAY_TYPE_PARAMS[i]));
        }
        choiceItemList.get(0).setSelect(true);
        mAdapter.notifyDataSetChanged();
        ((ScreenWindowAdapter) mAdapter).setListener(new SingleChoiceTextAdapter.OnItemClickListener() {
            @Override
            public void onClick(ChoiceItem item) {
                for (int i = 0; i < choiceItemList.size(); i++) {
                    choiceItemList.get(i).setSelect(false);
                }
                item.setSelect(true);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initCalendar() {
        super.initCalendar();
        startCalendar.set(Calendar.MONTH,endCalendar.get(Calendar.MONTH)-3);
    }

    @Override
    protected void reset() {
        initCalendar();
        initPickerText();
        for (int i = 0; i < choiceItemList.size(); i++) {
            choiceItemList.get(i).setSelect(false);
        }
        choiceItemList.get(0).setSelect(true);
    }

    @Override
    protected void submit() {
        if (listener != null) {
            if (endCalendar.compareTo(startCalendar) < 0) {
                ToastUtil.toast("结束时间不能比开始时间早");
                return;
            }
            String state = "";
            for (int i = 0; i < choiceItemList.size(); i++) {
                if (choiceItemList.get(i).isSelect()) {
                    state = choiceItemList.get(i).getId();
                }
            }
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            listener.submit(sp.format(startCalendar.getTime()), sp.format(endCalendar.getTime()), state);
            dismiss();
        }
    }

    private OnSubmitListener listener;

    public void setListener(OnSubmitListener listener) {
        this.listener = listener;
    }

    public interface OnSubmitListener {
        void submit(String startTime, String endTime, String state);
    }

}
