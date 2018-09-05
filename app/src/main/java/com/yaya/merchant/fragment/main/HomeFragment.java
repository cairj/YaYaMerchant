package com.yaya.merchant.fragment.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.GoodsAction;
import com.yaya.merchant.action.MainAction;
import com.yaya.merchant.activity.MainActivity;
import com.yaya.merchant.activity.account.BalanceAccountActivity;
import com.yaya.merchant.activity.account.EnterBillActivity;
import com.yaya.merchant.activity.account.MemberManagerActivity;
import com.yaya.merchant.activity.article.ArticleListActivity;
import com.yaya.merchant.activity.order.OrderListActivity;
import com.yaya.merchant.activity.user.MerchantListActivity;
import com.yaya.merchant.activity.user.VerificationActivity;
import com.yaya.merchant.activity.withdraw.WithdrawMoneyActivity;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.data.goods.Goods;
import com.yaya.merchant.data.main.HomeData;
import com.yaya.merchant.data.order.OrderDetail;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.LoadingUtil;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.util.UserHelper;
import com.yaya.merchant.widgets.GifPtrHeader;
import com.yaya.merchant.widgets.adapter.GoodsSaleRankAdapter;

import java.util.ArrayList;

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

    @BindView(R.id.tv_yesterday_order_count)
    protected TextView yesterdayOrderCountTv;
    @BindView(R.id.tv_yesterday_order_amount)
    protected TextView yesterdayOrderAmountTv;
    @BindView(R.id.tv_this_month_order_count)
    protected TextView thisMonthOrderCountTv;
    @BindView(R.id.tv_this_month_order_amount)
    protected TextView thisMonthOrderAmountTv;
    @BindView(R.id.rv_goods_sale_rank)
    protected RecyclerView goodsSaleRankRv;

    @BindView(R.id.tv_receivables)
    protected TextView receivablesTv;
    @BindView(R.id.ll_method_3)
    protected LinearLayout methodLL3;


    private GoodsSaleRankAdapter saleRankAdapter;
    private ArrayList<Goods> goodList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        super.initView();
        initPtrFrame();

        initGoodsSaleRank();

        if (UserHelper.isAgent()){
            receivablesTv.setCompoundDrawablesWithIntrinsicBounds(0,R.mipmap.home_ic_merchant,0,0);
            receivablesTv.setText("商户");
            methodLL3.setVisibility(View.GONE);
        }
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
                initData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    private void initGoodsSaleRank(){
        goodsSaleRankRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        saleRankAdapter = new GoodsSaleRankAdapter(goodList);
        goodsSaleRankRv.setAdapter(saleRankAdapter);
    }

    @Override
    protected void initData() {
        getData();
        getGoodsSaleRank();
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
                amountTv.setText(response.getResultData().getCollectionAmount());
                todayTransactionNumberTv.setText(response.getResultData().getOrderNumber());
                todayVisitorNumberTv.setText(response.getResultData().getOrderPrice());
                todayNewMemberNumberTv.setText(response.getResultData().getAddMemberCount());
                totalTransactionNumberTv.setText(response.getResultData().getOrderPriceTotal());
                totalMemberNumberTv.setText(response.getResultData().getMemberTotal());
                totalOrderNumberTv.setText(response.getResultData().getOrderPriceCount());

                yesterdayOrderCountTv.setText(response.getResultData().getYesterdayOrderTotal());
                yesterdayOrderAmountTv.setText(response.getResultData().getYesterdayOrderPrice());
                thisMonthOrderCountTv.setText(response.getResultData().getMonthOrderTotal());
                thisMonthOrderAmountTv.setText(response.getResultData().getMonthOrderPrice());

                if (getActivity() instanceof MainActivity && !TextUtils.isEmpty(response.getResultData().getOrderCount())){
                    ((MainActivity)getActivity()).setOrderCount(response.getResultData().getOrderCount());
                }
            }
        });
    }

    private void getGoodsSaleRank(){
        GoodsAction.queryGoodsSaleRank("", new GsonCallback<Goods>(Goods.class) {
            @Override
            public void onSucceed(JsonResponse<Goods> response) {
                goodList.clear();
                goodList.addAll(response.getDataList());
                saleRankAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.home_tv_amount,R.id.fl_total_member,R.id.fl_total_order_number,R.id.tv_cash,R.id.tv_receive,R.id.tv_return_review,
            R.id.tv_receivables,R.id.tv_balance_account,R.id.iv_news,R.id.tv_scan_payment,R.id.tv_online_payment})
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
                if (UserHelper.isMerchant()) {
                    new IntentIntegrator(getActivity())
                            .setOrientationLocked(false)
                            .setPrompt("将提货二维码放入框内即可自动扫描")
                            .setCaptureActivity(VerificationActivity.class) // 设置自定义的activity是VerificationActivity
                            .initiateScan(); // 初始化扫描
                }

                if (UserHelper.isAgent()){
                    openActivity(MerchantListActivity.class);
                }
                break;
            case R.id.iv_news:
                openActivity(ArticleListActivity.class);
                break;
            case R.id.tv_scan_payment:
                OrderListActivity.open(getActivity(), OrderDetail.ORDER_PAYMENT_TYPE_SCAN);
                break;
            case R.id.tv_online_payment:
                OrderListActivity.open(getActivity(), OrderDetail.ORDER_PAYMENT_TYPE_ONLINE);
                break;
            case R.id.tv_balance_account:
            case R.id.tv_return_review:
            case R.id.tv_receive:
                ToastUtil.toast("敬请期待");
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
