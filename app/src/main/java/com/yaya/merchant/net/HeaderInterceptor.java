package com.yaya.merchant.net;

import com.yaya.merchant.util.sp.SPUtil;
import com.yaya.merchant.util.sp.SpKeys;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    /*{         udid" : "88888888",
            "device" : "ios",
            "device-ver" : "2.2.1",
            "device-model" : "iphone 6",
            "app-ver" : "0.1.0",
            "app-ver-code":"5"
            "app-debug" : true,
            "channel" : "hequ"
            }*/

    // user-agent
// UA 获得系统的UA 在后面加上 merchant/APP版本号
//            如:Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_2_1 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5 qiguanbang/1.0.0

    @Override
    public Response intercept(Chain chain) throws IOException {


        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        /*       .addHeader("udid", DeviceParamsHelper.getUdid())
                .addHeader("device", DeviceParamsHelper.getDevice())
                .addHeader("device-ver", DeviceParamsHelper.getDeviceVer())
                .addHeader("device-model", DeviceParamsHelper.getDeviceModel())
                .addHeader("app-ver", DeviceParamsHelper.getAppVer())
                .addHeader(Constants.HTTP_REQUEST_HEADER_KEY_APP_VER_CODE, String.valueOf(DeviceParamsHelper.getAppVersionCode()))
                .addHeader("app-debug", DeviceParamsHelper.getAppDebug())
                .addHeader("channel", DeviceParamsHelper.getChannel())
                .addHeader(Constants.HTTP_REQUEST_HEADER_PUSH_TOKEN, PushAgent.getInstance(ServiceFactory.getContext()).getRegistrationId());
        */
        if (SPUtil.getBoolean(SpKeys.IS_LOGIN)){
            builder.addHeader("token", SPUtil.getString(SpKeys.TOKEN));
        }
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
