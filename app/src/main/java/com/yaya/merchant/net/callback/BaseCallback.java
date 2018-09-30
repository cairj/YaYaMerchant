package com.yaya.merchant.net.callback;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.toroke.okhttp.JsonResponse;
import com.toroke.okhttp.BaseData;
import com.yaya.merchant.util.AppManager;
import com.yaya.merchant.util.Constants;
import com.yaya.merchant.util.UserHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseCallback<K extends Serializable> extends Callback<JsonResponse<K>> {

    protected String JSON_KEY_DATA = "data";//单个结果
    protected String JSON_KEY_CODE = "code";//单个结果
    protected String JSON_KEY_MSG = "msg";//单个结果

    protected JsonResponse<K> jsonResponse;

    private View view;
    private List<View> mViewList;//请求过程中设置enable为false的控件

    public BaseCallback(){}

    public BaseCallback(View view){
        this.view = view;
    }

    public BaseCallback(List<View> viewList){mViewList = viewList;}

    @Override
    public JsonResponse<K> parseNetworkResponse(Response response, int i) throws Exception {
        jsonResponse = new JsonResponse<K>();
        try {
            String json = response.body().string();
            if (TextUtils.isEmpty(json)) return null;
            Log.e("body============",json);
            parse(jsonResponse, json);

            switch (jsonResponse.getCode()) {
                case 0:

                    break;
                case 101://token过期
                    UserHelper.logout(AppManager.getAppManager().getCurrentActivity());
                    break;
            }
            //如果要更新UI，需要使用handler，可以如下方式实现，也可以自己写handler
            OkHttpUtils.getInstance().getDelivery().execute(new Runnable() {
                @Override
                public void run() {
//                    ToastHelper.show("错误代码：" + jsonResponse.getCode() + "，错误信息：" + jsonResponse.getMsg());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("OkHttpUtils", "网络IO流读取错误");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("OkHttpUtils", "JSON解析异常");
        }
        return jsonResponse;
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        e.printStackTrace();
        if (e instanceof UnknownHostException){//没有网络连接
            //ToastHelper.show(R.string.net_unavailable_hint);
        }else if (e instanceof SocketTimeoutException){//网络连接超时
            //ToastHelper.show(R.string.net_time_out_hint);
        }
    }

    @Override
    public void onResponse(JsonResponse<K> kJsonResponse, int i){
        if (kJsonResponse.getCode() == Constants.RESPONSE_SUCCESS){
            onSucceed(kJsonResponse);
        }else {
            onFailed(kJsonResponse);
        }
       /* switch (){
            case 200:
                onSucceed(kJsonResponse);
                break;
            *//*case ApiCode.TOKEN_INVALID://token无效或者过期时，打开登录界面重新登录
                onTokenInvalid(kJsonResponse);
                break;*//*
            default:
                onFailed(kJsonResponse);
        }*/
    };

    public void parse(JsonResponse<K> jsonResponse, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        jsonResponse.setMsg(jsonObject.optString(JSON_KEY_MSG, ""));
        jsonResponse.setCode(jsonObject.optInt(JSON_KEY_CODE, 0));
        /* jsonResponse.setTip(jsonObject.optString(JSON_KEY_TIP, "")); ;
        jsonResponse.setRowNum(jsonObject.optInt(JSON_KEY_ROW_NUM, 0));
        jsonResponse.setCount(jsonObject.optInt(JSON_KEY_COUNT, 0));
        if (jsonObject.has(JSON_KEY_PAGEINFO)){
            jsonResponse.setPageInfo(new Gson().fromJson(jsonObject.getJSONObject(JSON_KEY_PAGEINFO).toString(), PageInfo.class));
        }*/

        parseResult(jsonObject);
        parseResults(jsonObject);
    }

    protected void parseResults(JSONObject jsonObject) throws JSONException {
        if (jsonResponse.getCode() == Constants.RESPONSE_SUCCESS && jsonObject.has(JSON_KEY_DATA) && (jsonObject.get(JSON_KEY_DATA) instanceof JSONArray)) {
            JSONArray resultsArray = jsonObject.getJSONArray(JSON_KEY_DATA);
            if (resultsArray != null) {
                jsonResponse.setDataList(parseItems(resultsArray));
            }
        }
    }

    public ArrayList<K> parseItems(JSONArray jsonArray) throws JSONException {
        ArrayList<K> mList = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++){
            JSONObject object = jsonArray.getJSONObject(i);
            mList.add(parseItem(object));
        }
        return mList;
    }

    protected void parseResult(JSONObject jsonObject) throws JSONException {
        if (jsonResponse.getCode() == Constants.RESPONSE_SUCCESS && jsonObject.has(JSON_KEY_DATA)) {
            if (!(jsonObject.get(JSON_KEY_DATA) instanceof JSONArray)) {
                if (jsonObject.get(JSON_KEY_DATA) instanceof String) {
                    jsonResponse.setData((K) jsonObject.getString(JSON_KEY_DATA));
                } else if (jsonObject.get(JSON_KEY_DATA) instanceof Integer) {
                    jsonResponse.setData((K) jsonObject.getString(JSON_KEY_DATA));
                } else {
                    JSONObject resultObject = jsonObject.getJSONObject(JSON_KEY_DATA);
                    jsonResponse.setData(parseItem(resultObject));
                }
            }
        }
    }

    /** 将jsonObject解析成单个对象 */
    public abstract K parseItem(JSONObject jsonObject) throws JSONException;

    /** 返回状态成功的回调 */
    public abstract void onSucceed(JsonResponse<K> response);

    /*public void onTokenInvalid(JsonResponse<K> response){
        if (AppManager.getAppManager().getCurrentActivity() == null) {
            return;
        }
        LoginActivity.open(AppManager.getAppManager().getCurrentActivity(), LoginActivity.REQUEST_CODE_TIME_OUT);
    }*/

    /** 返回状态失败的回调 */
    public abstract void onFailed(JsonResponse<K> response);

    public static boolean hasKeyValue(JSONObject json, String key) throws JSONException {
        return !json.isNull(key) && json.has(key) && !TextUtils.isEmpty(json.getString(key)) && !"null NULL".contains(json.getString(key));
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        if (view != null){
            view.setEnabled(false);
        }
        if (mViewList != null){
            for (View temp : mViewList){
                temp.setEnabled(false);
                temp.setClickable(false);
            }
        }
    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        if (view != null){
            view.setEnabled(true);
        }
        if (mViewList != null){
            for (View temp : mViewList){
                temp.setEnabled(true);
                temp.setClickable(true);
            }
        }
    }

    public JsonResponse<K> getJsonResponse() {
        return jsonResponse;
    }
}
