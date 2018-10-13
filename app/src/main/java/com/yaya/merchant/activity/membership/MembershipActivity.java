package com.yaya.merchant.activity.membership;

import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MembershipAction;
import com.yaya.merchant.base.activity.BasePtrRecycleActivity;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.data.membership.MemberShipData;
import com.yaya.merchant.data.membership.MembershipLevelData;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.Constants;
import com.yaya.merchant.util.DpPxUtil;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.widgets.adapter.MemberShipAdapter;
import com.yaya.merchant.widgets.adapter.SingleChoiceTextAdapter;
import com.yaya.merchant.widgets.popupwindow.SingleChoiceWindow;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 蔡蓉婕 on 2018/10/11.
 */

public class MembershipActivity extends BasePtrRecycleActivity<MemberShipData> {

    private String level;
    private String search;
    private boolean isClassicFinish;

    @BindView(R.id.fl_status)
    protected FrameLayout statusFl;

    @BindView(R.id.tv_status)
    protected TextView merchantStatusTv;

    @BindView(R.id.ll_search)
    protected LinearLayout searchLL;
    @BindView(R.id.search_ed_input)
    protected EditText inputEd;

    protected SingleChoiceWindow singleChoiceWindow;
    protected SingleChoiceTextAdapter singleChoiceAdapter;
    protected ArrayList<ChoiceItem> singleChoiceItemList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_membership;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        MemberShipAdapter adapter = new MemberShipAdapter(mDataList);
        adapter.setListener(new MemberShipAdapter.OnItemClickListener() {
            @Override
            public void onClick(MemberShipData merchantData) {

            }
        });
        return adapter;
    }

    @Override
    protected JsonResponse<BaseRowData<MemberShipData>> getData() throws Exception {
        return MembershipAction.getMerchantInfo(search,level);
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("会员");
        recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_F6F7F9));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ptrFrame.getLayoutParams();
        lp.topMargin = DpPxUtil.dp2px(10);
        ptrFrame.setLayoutParams(lp);

        merchantStatusTv.setText("会员分类");
        initSingleChoiceWindow();
        initInputEd();
    }

    @Override
    protected void initData() {
        super.initData();
        getMerchantClassify();
    }

    protected void initSingleChoiceWindow() {
        singleChoiceWindow = new SingleChoiceWindow(this);
        singleChoiceAdapter = new SingleChoiceTextAdapter(singleChoiceItemList);
        singleChoiceWindow.setAdapter(singleChoiceAdapter);
        singleChoiceWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                merchantStatusTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_down, 0);
            }
        });

        singleChoiceAdapter.setListener(new SingleChoiceTextAdapter.OnItemClickListener() {
            @Override
            public void onClick(ChoiceItem item) {
                for (ChoiceItem choiceItem : singleChoiceItemList) {
                    choiceItem.setSelect(false);
                }
                item.setSelect(true);
                singleChoiceAdapter.notifyDataSetChanged();
                merchantStatusTv.setText(item.getContent());
                level = item.getId();
                refresh();
                singleChoiceWindow.dismiss();
            }
        });
    }

    private void initInputEd(){
        inputEd.setHint("搜索电话/名称");
        inputEd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search = inputEd.getText().toString().trim();
                    mCurrentPos = Constants.DEFAULT_FIRST_PAGE_COUNT;
                    searchLL.setVisibility(View.GONE);
                    refresh();
                    inputEd.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.fl_search,R.id.fl_member_status,R.id.external_view})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.fl_search:
                searchLL.setVisibility(View.VISIBLE);
                break;
            case R.id.fl_member_status:
                if(!isClassicFinish){
                    ToastUtil.toast("正在获取分类");
                    return;
                }
                singleChoiceWindow.showDropDown(merchantStatusTv);
                break;
            case R.id.external_view:
                searchLL.setVisibility(View.GONE);
                break;
        }
    }

    private void getMerchantClassify(){
        MembershipAction.getMembershipLevel(new GsonCallback<MembershipLevelData>(MembershipLevelData.class) {
            @Override
            public void onSucceed(JsonResponse<MembershipLevelData> response) {
                for (int i = 0; i < response.getDataList().size(); i++) {
                    MembershipLevelData classic = response.getDataList().get(i);
                    ChoiceItem item = new ChoiceItem(classic.getLevelName(), classic.getLevelId());
                    singleChoiceItemList.add(item);
                }
                singleChoiceItemList.add(0,new ChoiceItem("全部分类", ""));
                singleChoiceItemList.get(0).setSelect(true);
                singleChoiceAdapter.notifyDataSetChanged();
                isClassicFinish = true;
            }
        });
    }

}
