package com.zrquan.mobile.controller;

import com.android.volley.toolbox.RequestFuture;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.UrlUtils;
import com.zrquan.mobile.support.volley.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class AutoMatchController {
    //自动匹配，同步返回
    public static JSONObject match(String type, String query) {
        //http://stackoverflow.com/questions/16904741/can-i-do-a-synchronous-request-with-volley?rq=1
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JSONObject response = null;


        String url = UrlUtils.getUrl("automatch");
        JSONObject params = new JSONObject();
        try {
            params.put("query", query);
            params.put("returnSize", 10);
            params.put("type", type);
        } catch (JSONException e) {
            LogUtils.d("ParseJsonError:", e);
        }

        VolleyJsonRequest.request(url, null, params, future, future);

        try {
            response = future.get(); // this will block
        } catch (InterruptedException | ExecutionException e) {
            // exception handling
            LogUtils.d("Request automatch fail...", e);
        }
        return response;
    }
}
