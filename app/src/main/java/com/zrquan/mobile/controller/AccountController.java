package com.zrquan.mobile.controller;

import com.android.volley.VolleyError;
import com.zrquan.mobile.event.AccountEvent;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.UrlUtils;
import com.zrquan.mobile.support.volley.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class AccountController {

    public static void sendVerifyCode(String phoneNum) {
        // pass second argument as "null" for GET requests
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phoneNum);
        final String url = UrlUtils.getUrlWithParams("users/send_verify_code", params);
        LogUtils.d(url);
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LogUtils.d("收到请求的回复了");
                    LogUtils.d("Response:" + response.toString(4));
                    JSONObject results = response.getJSONObject("results");
                    String verifyCode = results.getString("verify_code");
                    LogUtils.d("verify_code", verifyCode);
                    EventBus.getDefault().post(new AccountEvent(verifyCode));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
