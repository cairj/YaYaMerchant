package com.yaya.merchant.fragment.main;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.MainAction;
import com.yaya.merchant.activity.search.MerchantSearchActivity;
import com.yaya.merchant.activity.user.BankCardActivity;
import com.yaya.merchant.activity.user.ContractInformationActivity;
import com.yaya.merchant.activity.user.EmployeeManagerActivity;
import com.yaya.merchant.activity.user.FeedBackActivity;
import com.yaya.merchant.activity.user.InformationActivity;
import com.yaya.merchant.activity.user.MerchantManagerActivity;
import com.yaya.merchant.activity.user.MerchantQRCodeActivity;
import com.yaya.merchant.activity.user.SettingActivity;
import com.yaya.merchant.activity.user.UserInfoActivity;
import com.yaya.merchant.activity.user.VerificationActivity;
import com.yaya.merchant.activity.user.VoiceSettingActivity;
import com.yaya.merchant.base.fragment.BaseFragment;
import com.yaya.merchant.data.main.UserData;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.DialogUtil;
import com.yaya.merchant.util.LoadingUtil;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;
import com.yaya.merchant.widgets.GifPtrHeader;
import com.yaya.merchant.widgets.adapter.EmployeeManagerAdapter;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Request;

/**
 * Created by admin on 2018/3/7.
 */

public class UserFragment extends BaseFragment {

    private UserData userData;

    @BindView(R.id.user_iv_head)
    protected ImageView headIv;
    @BindView(R.id.user_tv_name)
    protected TextView nameTv;
    @BindView(R.id.user_tv_phone)
    protected TextView phoneTv;
    @BindView(R.id.user_tv_merchant_manager)
    protected TextView merchantManagerTv;
    @BindView(R.id.user_tv_version)
    protected TextView versionTv;
    @BindView(R.id.ptr_frame)
    protected PtrFrameLayout ptrFrame;
    @BindView(R.id.scrollView)
    protected ScrollView scrollView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView() {
        super.initView();
        initPtrFrame();

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (scrollView.getScrollY()>0){
                    ptrFrame.setEnabled(false);
                }else {
                    ptrFrame.setEnabled(true);
                }
                return false;
            }
        });
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
        initVersion();
        getData();
    }

    private void getData(){
        MainAction.getUserData(new GsonCallback<UserData>(UserData.class) {
            @Override
            public void onSucceed(JsonResponse<UserData> response) {
                userData = response.getResultData();
                GlideLoaderHelper.loadAvatar(userData.getHeadImgUrl(), headIv);
                nameTv.setText(userData.getName());
                phoneTv.setText(userData.getPhone());
                //merchantManagerTv.setText(userData.getStoreCount());
            }

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

        });
    }

    private void initVersion() {
        PackageInfo info = null;
        try {
            info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;
        versionTv.setText(version);
    }

    @OnClick({R.id.user_rl_merchant_manager, R.id.user_rl_merchant_qrcode,R.id.tv_my_info,
            R.id.user_rl_bank_card, R.id.user_rl_merchant_info, R.id.user_rl_set_voice,
            R.id.user_rl_info,R.id.user_rl_contact_service,R.id.user_rl_subscribe_info})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_rl_merchant_manager:
                openActivity(MerchantManagerActivity.class);
                break;
           /* case R.id.user_rl_employee_manager:
                openActivity(EmployeeManagerActivity.class);
                break;*/
            case R.id.user_rl_merchant_qrcode:
                openActivity(MerchantQRCodeActivity.class);
                break;
            case R.id.user_rl_bank_card:
                openActivity(BankCardActivity.class);
                break;
            case R.id.user_rl_merchant_info:
                openActivity(UserInfoActivity.class);
                break;
            case R.id.user_rl_set_voice:
                openActivity(VoiceSettingActivity.class);
                break;
            /*case R.id.user_rl_verification:
                new IntentIntegrator(getActivity())
                        .setOrientationLocked(false)
                        .setPrompt("将提货二维码放入框内即可自动扫描")
                        .setCaptureActivity(VerificationActivity.class) // 设置自定义的activity是VerificationActivity
                        .initiateScan(); // 初始化扫描
                break;*/
           /* case R.id.user_rl_feed_back:
                openActivity(FeedBackActivity.class);
                break;*/
            case R.id.user_rl_info:
                if(userData == null){
                    ToastUtil.toast("请稍等");
                    return;
                }
                SettingActivity.open(getActivity(),userData);
                break;
            case R.id.user_rl_contact_service:
                DialogUtil.chatToService(getActivity(),"");
                break;
            case R.id.tv_my_info:
                if(userData == null){
                    ToastUtil.toast("请稍等");
                    return;
                }
                InformationActivity.open(getActivity(),userData);
                break;
            case R.id.user_rl_subscribe_info:
                openActivity(ContractInformationActivity.class);
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
