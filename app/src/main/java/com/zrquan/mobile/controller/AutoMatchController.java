package com.zrquan.mobile.controller;

import com.android.volley.toolbox.RequestFuture;

import com.google.gson.JsonObject;

import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.UrlUtils;
import com.zrquan.mobile.support.volley.VolleyJsonRequest;

import java.util.concurrent.ExecutionException;

public class AutoMatchController {
    //自动匹配，同步返回
    public static JsonObject match(String type, String query) {
        //http://stackoverflow.com/questions/16904741/can-i-do-a-synchronous-request-with-volley?rq=1
        RequestFuture<JsonObject> future = RequestFuture.newFuture();
        JsonObject response = null;


        String url = UrlUtils.getUrl("automatch");
        JsonObject params = new JsonObject();
        params.addProperty("query", query);
        params.addProperty("returnSize", 10);
        params.addProperty("type", type);

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
