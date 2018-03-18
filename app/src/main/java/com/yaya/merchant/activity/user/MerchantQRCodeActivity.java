package com.yaya.merchant.activity.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.toroke.okhttp.JsonResponse;
import com.yaya.merchant.R;
import com.yaya.merchant.action.UserAction;
import com.yaya.merchant.base.activity.BaseActivity;
import com.yaya.merchant.net.callback.GsonCallback;
import com.yaya.merchant.util.ImageHelper;
import com.yaya.merchant.util.ToastUtil;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;
import com.zhy.http.okhttp.callback.Callback;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2018/3/17.
 */

public class MerchantQRCodeActivity extends BaseActivity {

    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 999;

    @BindView(R.id.iv_qr_code)
    ImageView qrCodeIv;

    private String storeId;
    private String storeName;
    private Bitmap storeQrCodeBitmap;

    public static void open(Context context, String storeId,String storeName) {
        Intent intent = new Intent(context, MerchantQRCodeActivity.class);
        intent.putExtra("storeId", storeId);
        intent.putExtra("storeName", storeName);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_2d_qr_code;
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBarTitle("门店二维码");
    }

    @Override
    protected void initData() {
        storeId = getIntent().getStringExtra("storeId");
        storeName = getIntent().getStringExtra("storeName");
        super.initData();
        UserAction.getMerchantQrCode(storeId, new Callback<byte[]>() {
            @Override
            public byte[] parseNetworkResponse(Response response, int i) throws Exception {
                return response.body().bytes();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.toast("加载失败");
            }

            @Override
            public void onResponse(byte[] s, int i) {
                storeQrCodeBitmap= BitmapFactory.decodeByteArray(s,0,s.length);
                qrCodeIv.setImageBitmap(storeQrCodeBitmap);
            }
        });
    }

    @OnClick(R.id.tv_save_qr_code)
    protected void onClick() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {//权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            //权限已经被授予，在这里直接写要执行的相应方法即可
            savePicture();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                savePicture();
            } else {
                ToastUtil.toast("请求权限失败，无法保存");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void savePicture(){
        ImageHelper.downloadImage(storeQrCodeBitmap,storeName+"_"+System.currentTimeMillis()+".png");
    }

}
