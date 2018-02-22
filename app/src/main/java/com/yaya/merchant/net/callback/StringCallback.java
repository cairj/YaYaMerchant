package com.yaya.merchant.net.callback;

import android.view.View;

import com.toroke.okhttp.JsonResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 魏新智 on 2017/4/21.
 */
public abstract class StringCallback extends BaseCallback<String>{

    public StringCallback(){
        super();
    }

    public StringCallback(List<View> viewList){
        super(viewList);
    }

    @Override
    public ArrayList<String> parseItems(JSONArray jsonArray) throws JSONException {
//        return super.parseItems(jsonArray);
        ArrayList<String> mList = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++){
            mList.add(jsonArray.getString(i));
        }
        return mList;
    }

    @Override
    protected void parseResult(JSONObject jsonObject) throws JSONException {
//        super.parseResult(jsonObject);
        if (jsonObject.has(JSON_KEY_DATA)){
            jsonResponse.setData(jsonObject.getString(JSON_KEY_DATA));
        }
    }

    @Override
    public String parseItem(JSONObject jsonObject) throws JSONException {
        return null;
    }

    @Override
    public void onFailed(JsonResponse<String> response) {
        /*ToastHelper.show(response.getMsg());*/
    }
}
