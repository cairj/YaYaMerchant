package com.yaya.merchant.fragment.account;

import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toroke.okhttp.BaseRowData;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainDataAction;
import com.yaya.merchant.activity.search.MemberSearchActivity;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.base.fragment.BasePtrRecycleFragment;
import com.yaya.merchant.data.ChoiceItem;
import com.yaya.merchant.data.account.Member;
import com.yaya.merchant.widgets.adapter.MemberManagerAdapter;
import com.yaya.merchant.widgets.adapter.SingleChoiceTextAdapter;
import com.yaya.merchant.widgets.popupwindow.SingleChoiceWindow;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/14.
 */

public class MemberManagerFragment extends BasePtrRecycleFragment<Member> {

    private String memberState;

    @BindView(R.id.tv_status)
    protected TextView memberStatusTv;

    protected SingleChoiceWindow singleChoiceWindow;
    protected SingleChoiceTextAdapter singleChoiceAdapter;
    protected ArrayList<ChoiceItem> singleChoiceItemList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_member_manager;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MemberManagerAdapter(mDataList);
    }

    @Override
    protected JsonResponse<BaseRowData<Member>> getData() throws Exception {
        return MainDataAction.getMemberList("", memberState, String.valueOf(mCurrentPos), String.valueOf(pageSize));
    }

    @Override
    public String getTitle() {
        return "管理";
    }

    @Override
    protected void initView() {
        super.initView();
        initSingleChoiceWindow();
    }

    protected void initSingleChoiceWindow() {
        singleChoiceWindow = new SingleChoiceWindow(getActivity());
        singleChoiceAdapter = new SingleChoiceTextAdapter(singleChoiceItemList);
        singleChoiceWindow.setAdapter(singleChoiceAdapter);
        singleChoiceWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                memberStatusTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_down, 0);
            }
        });
        for (int i = 0; i < Member.BIND_TYPE.length; i++) {
            ChoiceItem item = new ChoiceItem(Member.BIND_TYPE[i], Member.BIND_TYPE_ID[i]);
            singleChoiceItemList.add(item);
        }
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
                memberStatusTv.setText(item.getContent());
                memberState = item.getId();
                refresh();
                singleChoiceWindow.dismiss();
            }
        });
    }


    @OnClick({R.id.fl_search,R.id.fl_member_status})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_search:
                openActivity(MemberSearchActivity.class);
                break;
            case R.id.fl_member_status:
                singleChoiceWindow.showDropDown(memberStatusTv);
                break;
        }
    }

}
