package com.yaya.merchant.base.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yaya.merchant.BuildConfig;
import com.yaya.merchant.R;
import com.yaya.merchant.net.ConnectionWatcher;
import com.yaya.merchant.util.LoadingUtil;
import com.yaya.merchant.util.sp.SPUtil;
import com.yaya.merchant.util.sp.SpKeys;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by admin on 2018/4/1.
 */

public class BaseWebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    protected WebView webView;
    @BindView(R.id.net_error_view)
    protected LinearLayout netErrorView;

    protected Map<String, String> header;

    protected String url;
    protected String mTitle;

    protected boolean isRefreshWebView = false;

    private FrameLayout mRootView;//界面根布局，全屏视频容器
    private View customView = null;//全屏时生成的view
    private WebChromeClient.CustomViewCallback myCallback = null;

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRefreshWebView = true;
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((ViewGroup) webView.getParent()).removeView(webView);
        webView.destroy();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        super.initView();
        webView.setWebViewClient(new MerchantWebViewClient());
        webView.setWebChromeClient(new BaseWebChromeClient());
        //开启调试模式
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setPluginState(WebSettings.PluginState.ON);
        setting.setAppCacheEnabled(true);

        mRootView = (FrameLayout) findViewById(android.R.id.content);
    }

    @Override
    protected void initData() {
        super.initData();
        if (!TextUtils.isEmpty(mTitle)){
            titleTv.setText(mTitle);
        }
        loadUrl();
    }

    protected void loadUrl(){
        if (ConnectionWatcher.isNetConnected(this)){
            hideNetErrorView();
            if (!TextUtils.isEmpty(url)){
                webView.loadUrl(url, buildHttpHeaders());
            }
        }else {
            showNetErrorView();
        }
    }

    protected void loadUrl(String url){
        this.url = url;
        loadUrl();
    }

    private class MerchantWebViewClient extends WebViewClient {
        //在webview内打开url
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            overrideUrlLoading(view, url);
            return true;
            /*if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            if (url.startsWith(IntentConstants.SCHEME)){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            WebView.HitTestResult hit = webView.getHitTestResult();
            int hitType = hit.getType();
            if(hitType == 0){//重定向时hitType为0
                overrideUrlLoading(view, url);
                return false;//不捕获302重定向
            }else{
                overrideUrlLoading(view, url);
                return true;
            }*/
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgress();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            onWebViewLoadFinished();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
//            showNetErrorView();
        }
    }

    private class BaseWebChromeClient extends WebChromeClient{
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            onReceivedWebTitle(view, title);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            showCustomView(view, callback);
        }

        public void onHideCustomView() {
            hideCustomView();
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    protected void onReceivedWebTitle(WebView view, String title){}

    protected void onWebViewLoadFinished() {
        hideProgress();
    }

    protected void overrideUrlLoading(WebView view, String url){
        view.loadUrl(url);
    }

    protected void showProgress() {
        LoadingUtil.showAsyncProgressDialog(this);
    }

    protected void hideProgress() {
        LoadingUtil.hideProcessingIndicator();
    }

    protected void onRefreshBtnClick(){
        loadUrl();
    }

    protected void showNetErrorView(){
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        webView.setVisibility(View.GONE);
        netErrorView.setVisibility(View.VISIBLE);
    }

    protected void hideNetErrorView(){
        webView.setVisibility(View.VISIBLE);
        netErrorView.setVisibility(View.GONE);
    }

    //设置网络请求头
    protected Map<String, String> buildHttpHeaders(){
        Map<String, String> headers = new HashMap<>();

        String versionName = BuildConfig.VERSION_NAME;
        String userAgent = webView.getSettings().getUserAgentString()+" qiguanbang/"+versionName+"/versionCode:"+BuildConfig.VERSION_CODE;
        webView.getSettings().setUserAgentString(userAgent);//添加user-agent

       /* headers.put(Constants.WEB_VIEW_REQUEST_HEADER_KEY_UA, webView.getSettings().getUserAgentString()+" qiguanbang/"+versionName);
        headers.put(Constants.HTTP_REQUEST_HEADER_KEY_UDID, DeviceParamsHelper.getUdid());
        headers.put(Constants.HTTP_REQUEST_HEADER_KEY_DEVICE, DeviceParamsHelper.getDevice());
        headers.put(Constants.HTTP_REQUEST_HEADER_KEY_DEVICE_VER, DeviceParamsHelper.getDeviceVer());
        headers.put(Constants.HTTP_REQUEST_HEADER_KEY_DEVICE_MODEL, DeviceParamsHelper.getDeviceModel());
        headers.put(Constants.HTTP_REQUEST_HEADER_KEY_APP_VER, DeviceParamsHelper.getAppVer());
        headers.put(Constants.HTTP_REQUEST_HEADER_KEY_APP_VER_CODE, String.valueOf(DeviceParamsHelper.getAppVersionCode()));
        headers.put(Constants.HTTP_REQUEST_HEADEUR_KEY_APP_DEBUG, DeviceParamsHelper.getAppDebug());
        headers.put(Constants.HTTP_REQUEST_HEADER_KEY_CHANNEL, DeviceParamsHelper.getChannel());
        if (MemberHelper.isLogin()){
            headers.put(Constants.HTTP_REQUEST_HEADER_KEY_QTOKEN, SPUtil.getString(SPKeys.TOKEN));
        }*/
        headers.put("Authorization", SPUtil.getString(SpKeys.TOKEN));
        return headers;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (customView != null) {//如果当前为全屏，则先退出全屏，否则finish
            hideCustomView();
        } else {
            finish();
        }
    }

    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        if (myCallback != null) {
            myCallback.onCustomViewHidden();
            myCallback = null;
            return;
        }

        customView = view;
        myCallback = callback;

        mRootView.addView(view);
        view.bringToFront();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//设置横屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
    }

    private void hideCustomView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置竖屏
        // 取消全屏
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (customView == null) {
            return;
        }
        mRootView.removeView(customView);
        customView = null;

        try {
            myCallback.onCustomViewHidden();
            myCallback = null;
        } catch (Exception e) {
        }
    }


}
