package com.zrquan.mobile.controller;

import com.android.volley.VolleyError;
import com.zrquan.mobile.event.AccountEvent;
import com.zrquan.mobile.event.VerifyAccountEvent;
import com.zrquan.mobile.support.util.LogUtils;
import com.zrquan.mobile.support.util.UrlUtils;
import com.zrquan.mobile.support.volley.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class AccountController {

    //发送验证码
    public static void sendVerifyCode(String phoneNum) {
        // pass second argument as "null" for GET requests
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phoneNum);
        String url = UrlUtils.getUrlWithParams("users/send_verify_code", params);
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
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

    //根据AccessToken验证当前已登录用户
    public static void verifyAccount(String accessToken) {
        VolleyJsonRequest.setAccessToken(accessToken);
        String url = UrlUtils.getUrl("users/verify");
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LogUtils.d("Response:" + response.toString(4));
                    String code = response.getString("code");
                    if(code.equals("S_OK")) {
                        EventBus.getDefault().post(new VerifyAccountEvent(VerifyAccountEvent.S_OK));
                    }
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
