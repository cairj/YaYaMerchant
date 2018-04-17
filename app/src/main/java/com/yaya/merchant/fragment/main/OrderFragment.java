package com.yaya.merchant.fragment.main;

import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainAction;
import com.yaya.merchant.activity.MainActivity;
import com.yaya.merchant.activity.article.ArticleListActivity;
import com.yaya.merchant.activity.order.OrderListActivity;
import com.yaya.merchant.activity.user.VerificationActivity;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.data.main.OrderData;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.EventBusTags;
import com.yaya.merchant.util.LoadingUtil;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.widgets.GifPtrHeader;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Request;

/**
 * Created by admin on 2018/3/7.
 */

public class OrderFragment extends BaseFragment {

    @BindView(R.id.order_tv_amount)
    protected TextView amountTv;
    @BindView(R.id.tv_already_order)
    protected TextView alreadyOrderTv;
    @BindView(R.id.tv_waiting_receive)
    protected TextView waitingReceiveTv;
    @BindView(R.id.tv_return)
    protected TextView returnTv;
    @BindView(R.id.tv_receive_count)
    protected TextView receiveCountTv;
    @BindView(R.id.tv_return_count)
    protected TextView returnCountTv;
    @BindView(R.id.ptr_frame)
    protected PtrFrameLayout ptrFrame;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_order;
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
        super.initData();
        getData();
    }

    private void getData(){
        MainAction.getOrderData(new GsonCallback<OrderData>(OrderData.class) {

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
            public void onSucceed(JsonResponse<OrderData> response) {
                OrderData data = response.getData().getData();
                amountTv.setText(data.getPricetotal());
                alreadyOrderTv.setText(data.getOrderprice());
                waitingReceiveTv.setText(data.getWaitReceivePrice());
                returnTv.setText(data.getRefundPrice());
                if (Integer.parseInt(data.getWaitReceiveCount())>0) {
                    receiveCountTv.setText(data.getWaitReceiveCount());
                    receiveCountTv.setVisibility(View.VISIBLE);
                }else {
                    receiveCountTv.setVisibility(View.GONE);
                }
                if (Integer.parseInt(data.getRefundCount())>0) {
                    returnCountTv.setText(data.getRefundCount());
                    returnCountTv.setVisibility(View.VISIBLE);
                }else {
                    returnCountTv.setVisibility(View.GONE);
                }

                if (getActivity() instanceof MainActivity){
                    ((MainActivity)getActivity()).setOrderCount(data.getOrderCount());
                }

            }
        });
    }

    @OnClick({R.id.order_tv_amount,R.id.tv_receive,R.id.tv_return_review,R.id.tv_verification,
            R.id.tv_push_goods,R.id.tv_goods_class,R.id.tv_release_activity,R.id.iv_news})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.order_tv_amount:
                OrderListActivity.open(getActivity(), com.yaya.merchant.data.order.OrderDetail.TYPE_ORDER_LIST);
                break;
            case R.id.tv_receive:
                OrderListActivity.open(getActivity(), com.yaya.merchant.data.order.OrderDetail.TYPE_DELIVER_ORDER_LIST);
                break;
            case R.id.tv_return_review:
                OrderListActivity.open(getActivity(), com.yaya.merchant.data.order.OrderDetail.TYPE_REFUND_ORDER_LIST);
                break;
            case R.id.tv_verification:
                new IntentIntegrator(getActivity())
                        .setOrientationLocked(false)
                        .setPrompt("将提货二维码放入框内即可自动扫描")
                        .setCaptureActivity(VerificationActivity.class) // 设置自定义的activity是VerificationActivity
                        .initiateScan(); // 初始化扫描
                break;
            case R.id.iv_news:
                openActivity(ArticleListActivity.class);
                break;
            case R.id.tv_push_goods:
            case R.id.tv_goods_class:
            case R.id.tv_release_activity:
                ToastUtil.toast("功能开发中");
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

    @Subscriber(tag= EventBusTags.DELIVER_ORDER_SUCCESS)
    private void deliverOrderSuccess(String str){
        getData();
    }
}
