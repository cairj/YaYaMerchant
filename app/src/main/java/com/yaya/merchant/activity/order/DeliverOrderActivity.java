package com.yaya.merchant.activity.order;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.OrderAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.data.order.ExpressCompany;
import com.yaya.merchant.data.order.OrderDetail;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.StatusBarUtil;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.widgets.adapter.CompanyChoiceAdapter;
import com.yaya.merchant.widgets.popupwindow.SingleChoiceWindow;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/3/25.
 */

public class DeliverOrderActivity extends BaseActivity {

    private SingleChoiceWindow companyChoiceWindow;
    private CompanyChoiceAdapter companyChoiceAdapter;
    private List<ExpressCompany> companyChoiceItemList = new ArrayList<>();

    private OrderDetail orderDetail;
    private ExpressCompany chooseExpressCompany;

    @BindView(R.id.tv_company)
    protected TextView companyTv;
    @BindView(R.id.edit_input_express_number)
    protected EditText expressNumberEdit;

    public static void open(Context context, OrderDetail orderDetail) {
        Intent intent = new Intent(context, DeliverOrderActivity.class);
        intent.putExtra("orderDetail", orderDetail);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_deliver_order;
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("发货");
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        orderDetail = (OrderDetail) getIntent().getSerializableExtra("orderDetail");
        companyChoiceWindow = new SingleChoiceWindow(this);
        companyChoiceAdapter = new CompanyChoiceAdapter(companyChoiceItemList);
        companyChoiceWindow.setAdapter(companyChoiceAdapter);
        companyChoiceWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                companyTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_down, 0);
            }
        });
        companyChoiceAdapter.setListener(new CompanyChoiceAdapter.OnItemClickListener() {
            @Override
            public void onClick(ExpressCompany expressCompany) {
                for (ExpressCompany choiceItem : companyChoiceItemList) {
                    choiceItem.setSelect(false);
                }
                expressCompany.setSelect(true);
                companyChoiceAdapter.notifyDataSetChanged();
                chooseExpressCompany = expressCompany;
                companyTv.setText("快递公司：" + expressCompany.getExpressName());
                companyChoiceWindow.dismiss();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        OrderAction.getExpressCompanyList(new GsonCallback<ExpressCompany>(ExpressCompany.class) {
            @Override
            public void onSucceed(JsonResponse<ExpressCompany> response) {
                companyChoiceItemList.addAll(response.getData().getDatas());
                companyChoiceAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.tv_company, R.id.tv_submit})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_company:
                companyTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_up, 0);
                companyChoiceWindow.showDropDown(companyTv);
                break;
            case R.id.tv_submit:
                OrderAction.deliverGoods(chooseExpressCompany, orderDetail, expressNumberEdit.getText().toString().trim(),
                        new GsonCallback<String>(String.class) {
                            @Override
                            public void onSucceed(JsonResponse<String> response) {
                                ToastUtil.toast(response.getData().getData());
                            }
                        });
                break;
        }
    }

}
