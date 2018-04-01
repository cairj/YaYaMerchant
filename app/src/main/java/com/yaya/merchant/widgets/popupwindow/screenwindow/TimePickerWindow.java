package com.yaya.merchant.widgets.popupwindow.screenwindow;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaya.merchant.R;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.widgets.adapter.ScreenWindowAdapter;
import com.yaya.merchant.widgets.adapter.SingleChoiceTextAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;

/**
 * Created by admin on 2018/4/1.
 */

public class TimePickerWindow extends BaseScreenPopupWindow {

    @BindView(R.id.ll_phone)
    protected LinearLayout phoneLL;
    @BindView(R.id.ed_phone)
    protected EditText phoneEd;
    @BindView(R.id.tv_hint)
    protected TextView hintTv;
    @BindView(R.id.tv_choose_title)
    protected TextView chooseTitleTv;
    @BindView(R.id.tv_time_title)
    protected TextView timeTitleTv;

    public TimePickerWindow(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();
        chooseTitleTv.setText("选择时间");
        timeTitleTv.setText("自定义时间");
    }

    @Override
    protected void initAdapter() {
        itemWidth = 106;
        super.initAdapter();
        choiceItemList.add(new ChoiceItem("今日","1"));
        choiceItemList.add(new ChoiceItem("最近七日","2"));
        choiceItemList.get(0).setSelect(true);
        mAdapter.notifyDataSetChanged();
        ((ScreenWindowAdapter)mAdapter).setListener(new SingleChoiceTextAdapter.OnItemClickListener() {
            @Override
            public void onClick(ChoiceItem item) {
                if (item.getId().equals("1")){
                    initCalendar();
                    initPickerText();
                }else if (item.getId().equals("2")){
                    initCalendar();
                    startCalendar.set(Calendar.DATE,endCalendar.get(Calendar.DATE)-7);
                    initPickerText();
                }
                for (int i = 0;i<choiceItemList.size();i++){
                    choiceItemList.get(i).setSelect(false);
                }
                item.setSelect(true);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void setPhoneLLVisible(boolean visible){
        phoneLL.setVisibility(visible? View.VISIBLE:View.GONE);
    }

    public void setHintTvVisible(boolean visible){
        hintTv.setVisibility(visible? View.VISIBLE:View.GONE);
    }

    @Override
    protected void afterChooseDate() {
        for (int i = 0;i<choiceItemList.size();i++){
            choiceItemList.get(i).setSelect(false);
        }
    }

    @Override
    protected void reset() {
        initCalendar();
        initPickerText();
        phoneEd.setText("");
        for (int i = 0;i<choiceItemList.size();i++){
            choiceItemList.get(i).setSelect(false);
        }
        choiceItemList.get(0).setSelect(true);
    }

    @Override
    protected void submit() {
        if (listener!=null){
            if (endCalendar.compareTo(startCalendar)<0){
                ToastUtil.toast("结束时间不能比开始时间早");
                return;
            }
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            listener.submit(sp.format(startCalendar.getTime()),sp.format(endCalendar.getTime()),
                    phoneEd.getText().toString().trim());
            dismiss();
        }
    }

    private OnSubmitListener listener;

    public void setListener(OnSubmitListener listener) {
        this.listener = listener;
    }

    public interface OnSubmitListener{
        void submit(String startTime,String endTime,String phone);
    }

}
