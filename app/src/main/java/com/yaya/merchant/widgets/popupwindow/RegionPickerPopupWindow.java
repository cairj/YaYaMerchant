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
import com.yaya.merchant.data.Region;
import com.yaya.merchant.util.dao.RegionDao;
import com.yaya.merchant.widgets.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.yaya.merchant.widgets.aigestudio.wheelpicker.view.WheelCurvedPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * 省市县3级联动选择器
 */
public class RegionPickerPopupWindow extends PopupWindow {

    public static final String DEFAULT_VALUE = "----";

    private Context context;

    private View windowView;
    private TextView cancelTv;
    private TextView submitTv;
    private View externalView;

    private WheelCurvedPicker provincePicker;
    private WheelCurvedPicker cityPicker;
    private WheelCurvedPicker districtPicker;

    private List<Region> provinceList;
    private List<Region> cityList;
    private List<Region> districtList;

    private int selectedProvinceIndex;
    private int selectedCityIndex;
    private int selectedDistrictIndex;

    private List<String> provinceNameList;
    private List<String> cityNameList;
    private List<String> districtNameList;

    private RegionDao regionDao;

    public RegionPickerPopupWindow(Context context){
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



    private void initView(){
        cancelTv = (TextView)windowView.findViewById(R.id.cancel_tv);
        submitTv = (TextView)windowView.findViewById(R.id.submit_tv);
        externalView = windowView.findViewById(R.id.external_view);

        provincePicker = (WheelCurvedPicker)windowView.findViewById(R.id.province_picker);
        cityPicker = (WheelCurvedPicker)windowView.findViewById(R.id.city_picker);
        districtPicker = (WheelCurvedPicker)windowView.findViewById(R.id.district_picker);
    }

    private void initData(){
        regionDao = new RegionDao(context);
        //初始化的省市县数据
        provinceList = regionDao.query(Region.PROVINCE_LEVEL);
        provinceNameList = getRegionNameList(provinceList);
        provincePicker.setData(provinceNameList);
        refreshCity(0, provinceNameList.get(0));
        refreshDistrict(0);

        /*cityList = regionDao.queryChildren(provinceList.get(0).getId());
        districtList = regionDao.queryChildren(cityList.get(0).getId());


        cityNameList = getRegionNameList(cityList);
        districtNameList = getRegionNameList(districtList);

        provincePicker.setItemIndex(2);

        provincePicker.setItemIndex(2);
        cityPicker.setData(cityNameList);
        districtPicker.setData(districtNameList);*/
    }

    private void initListener(){
        provincePicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {}

            @Override
            public void onWheelSelected(int index, String data) {
                selectedProvinceIndex = index;
                refreshCity(index, data);
            }

            @Override
            public void onWheelScrollStateChanged(int state) {}
        });

        cityPicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {}

            @Override
            public void onWheelSelected(int index, String data) {
                selectedCityIndex = index;
                refreshDistrict(index);
            }

            @Override
            public void onWheelScrollStateChanged(int state) {}
        });

        districtPicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {}

            @Override
            public void onWheelSelected(int index, String data) {
                selectedDistrictIndex = index;
            }

            @Override
            public void onWheelScrollStateChanged(int state) {}
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
//                provincePicker.setItemIndex(++testIndex);
            }
        });

        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSubmitTvClick(provinceList.get(selectedProvinceIndex),
                            cityList.get(selectedCityIndex), districtList.get(selectedDistrictIndex));
                    dismiss();
                }
                /*testIndex = 0;
                provincePicker.setItemIndex(testIndex);*/
            }
        });

        externalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private int testIndex=0;

    /**
     * 刷新城市picker的内容
     * @param provinceIndex
     * @param provinceName
     */
    private void refreshCity(int provinceIndex, String provinceName){
        //获取城市数据和名字
        cityList = regionDao.queryChildren(provinceList.get(provinceIndex).getId());
        cityNameList = getRegionNameList(cityList);

        //如果第一个名称与省相同（直辖市），则把名字改成“----”
        if (cityNameList.get(0).equals(provinceName)){
            cityNameList.remove(0);
            cityNameList.add(0, DEFAULT_VALUE);
            refreshDistrict(0);
        }

        //给城市picker设置内容，并
        cityPicker.setData(cityNameList);
        cityPicker.setItemIndex(0);
    }

    /**
     * 刷新县区picker的内容
     * @param cityIndex
     */
    private void refreshDistrict(int cityIndex){
        districtList = regionDao.queryChildren(cityList.get(cityIndex).getId());
        districtNameList = getRegionNameList(districtList);
        if (districtList == null || districtNameList == null || districtList.size() == 0 || districtNameList.size() == 0){
            /*LogHelper.e("地区选择器：城市id："+cityList.get(cityIndex).getId()+"没有子地区");*/
        }
        //如果第一个名称是“全市”，则替换成“-----”
        if (districtNameList.get(0).equals("全市")) {
            districtNameList.remove(0);
            districtNameList.add(0, DEFAULT_VALUE);
        }

        districtPicker.setData(districtNameList);
        districtPicker.setItemIndex(0);
    }

    public void show(){
        if (!this.isShowing()) {// 以下拉方式显示popupwindow
            this.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();
        }
    }

    private OnSubmitTvClickListener listener;

    public interface OnSubmitTvClickListener{
        public void onSubmitTvClick(Region province, Region city, Region district);
    }

    public void setOnSubmitTvClickListener(OnSubmitTvClickListener listener){
        this.listener = listener;
    }

    private List<String> getRegionNameList(List<Region> regionList){
        List<String> stringList = new ArrayList<>();
        for (Region region : regionList){
            stringList.add(region.getName());
        }
        return stringList;
    }

    //拼接省市县的名称，去除重复的名称（如直辖市：北京市北京市）和“全市”
    public static String getAddress(Region province, Region city, Region district){
        String address = "";
        if (city.getName().equals(province.getName())){
            address = province.getName();
        }else {
            address = province.getName() + city.getName();
        }
        if (!district.getName().equals("全市")){
            address = address + district.getName();
        }
        return address;
    }

    //只返回最后一级的名称
    public static String getShortAddress(Region province, Region city, Region district){
        if (district != null && !district.getName().equals("全市")){
            return district.getName();
        }
        if (city != null){
            return city.getName();
        }
        if (province != null){
            return province.getName();
        }
        return "";
    }
}
