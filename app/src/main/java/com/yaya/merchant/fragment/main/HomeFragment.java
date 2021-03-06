package com.yaya.merchant.fragment.main;

import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainAction;
import com.yaya.merchant.activity.MainActivity;
import com.yaya.merchant.activity.account.BalanceAccountActivity;
import com.yaya.merchant.activity.account.EnterBillActivity;
import com.yaya.merchant.activity.account.MemberManagerActivity;
import com.yaya.merchant.activity.article.ArticleListActivity;
import com.yaya.merchant.activity.user.VerificationActivity;
import com.yaya.merchant.activity.withdraw.WithdrawMoneyActivity;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.data.main.HomeData;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.LoadingUtil;
import com.yaya.merchant.widgets.GifPtrHeader;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Request;

/**
 * Created by admin on 2018/3/6.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.home_tv_amount)
    protected TextView amountTv;
    @BindView(R.id.tv_today_transaction_number)
    protected TextView todayTransactionNumberTv;
    @BindView(R.id.tv_today_visitor_number)
    protected TextView todayVisitorNumberTv;
    @BindView(R.id.tv_today_new_member_number)
    protected TextView todayNewMemberNumberTv;
    @BindView(R.id.tv_total_transaction_number)
    protected TextView totalTransactionNumberTv;
    @BindView(R.id.tv_total_member_number)
    protected TextView totalMemberNumberTv;
    @BindView(R.id.tv_total_order_number)
    protected TextView totalOrderNumberTv;
    @BindView(R.id.ptr_frame)
    protected PtrFrameLayout ptrFrame;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        super.initView();
        initPtrFrame();
    }

    /** 设置ptr */
    protected void initPtrFrame(){
        setPtrHandler();

        //设置下拉header
        GifPtrHeader header = new GifPtrHeader(getActivity());
        ptrFrame.setHeaderView(header);
        ptrFrame.addPtrUIHandler(header);
    }

    //设置下拉事件
    protected void setPtrHandler(){
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData(){
        MainAction.getHomeData(new GsonCallback<HomeData>(HomeData.class) {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request,id);
                LoadingUtil.showAsyncProgressDialog(getActivity(),"正在加载数据");
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                LoadingUtil.hideProcessingIndicator();
                if (ptrFrame != null){
                    ptrFrame.post(new Runnable() {
                        @Override
                        public void run() {
                            ptrFrame.refreshComplete();
                        }
                    });
                }
            }

            @Override
            public void onSucceed(JsonResponse<HomeData> response) {
                amountTv.setText(response.getData().getData().getCollectionAmount());
                todayTransactionNumberTv.setText(response.getData().getData().getOrderNumber());
                todayVisitorNumberTv.setText(response.getData().getData().getVisitorCount());
                todayNewMemberNumberTv.setText(response.getData().getData().getAddMemberCount());
                totalTransactionNumberTv.setText(response.getData().getData().getOrderPriceTotal());
                totalMemberNumberTv.setText(response.getData().getData().getMemberTotal());
                totalOrderNumberTv.setText(response.getData().getData().getOrderPriceCount());

                if (getActivity() instanceof MainActivity){
                    ((MainActivity)getActivity()).setOrderCount(response.getData().getData().getOrderCount());
                }
            }
        });
    }

    @OnClick({R.id.home_tv_amount,R.id.fl_total_member,R.id.fl_total_order_number,R.id.tv_cash,
            R.id.tv_receivables,R.id.tv_balance_account,R.id.iv_news})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.home_tv_amount:
                openActivity(EnterBillActivity.class);
                break;
            case R.id.fl_total_order_number:
                openActivity(EnterBillActivity.class);
                break;
            case R.id.fl_total_member:
                openActivity(MemberManagerActivity.class);
                break;
            case R.id.tv_cash:
                openActivity(WithdrawMoneyActivity.class);
                break;
            case R.id.tv_receivables:
                new IntentIntegrator(getActivity())
                        .setOrientationLocked(false)
                        .setPrompt("将提货二维码放入框内即可自动扫描")
                        .setCaptureActivity(VerificationActivity.class) // 设置自定义的activity是VerificationActivity
                        .initiateScan(); // 初始化扫描
                break;
            case R.id.tv_balance_account:
                openActivity(BalanceAccountActivity.class);
                break;
            case R.id.iv_news:
                openActivity(ArticleListActivity.class);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            getData();
        }
    }

}
