package com.zrquan.mobile.controller;

import com.android.volley.VolleyError;
import com.zrquan.mobile.event.AccountEvent;
import com.zrquan.mobile.support.enums.EventCode;
import com.zrquan.mobile.support.enums.EventType;
import com.zrquan.mobile.support.enums.ServerCode;
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
                    String code = response.getString("code");
                    if(code.equals(ServerCode.S_OK.name())) {
                        AccountEvent accountEvent = new AccountEvent();
                        accountEvent.setEventType(EventType.AE_NET_SEND_VERIFY_CODE);
                        accountEvent.setEventCode(EventCode.S_OK);
                        accountEvent.setServerCode(ServerCode.S_OK);
                        JSONObject results = response.getJSONObject("results");
                        String verifyCode = results.getString("verify_code");
                        accountEvent.setVerifyCode(verifyCode);
                        EventBus.getDefault().post(accountEvent);
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

    //根据AccessToken验证当前已登录用户
    public static void verifyAccount(final String accessToken) {
        VolleyJsonRequest.setAccessToken(accessToken);
        String url = UrlUtils.getUrl("users/verify");
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LogUtils.d("Response:" + response.toString(4));
                    String code = response.getString("code");
                    if(code.equals(ServerCode.S_OK.name())) {
                        AccountEvent accountEvent = new AccountEvent();
                        accountEvent.setEventType(EventType.AE_NET_VERIFY_JWT);
                        accountEvent.setEventCode(EventCode.S_OK);
                        accountEvent.setServerCode(ServerCode.S_OK);
                        EventBus.getDefault().post(accountEvent);
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

    public static void registerAccount(String verifyCode, String phoneNum, String password, String trueName
            , int industryId, String latestSchoolName) {
        String url = UrlUtils.getUrl("users/registration");

        JSONObject params = new JSONObject();
        try {
            params.put("verify_code", verifyCode);
            params.put("mobile", phoneNum);
            params.put("password", password);
            params.put("name", trueName);
            params.put("industry_id", industryId);
            params.put("latest_school_name", latestSchoolName);
        } catch (JSONException e) {
            LogUtils.d("ParseJsonError:", e);
        }

        VolleyJsonRequest.post(url, params, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LogUtils.d("Response:" + response.toString(4));
                    String code = response.getString("code");
                    AccountEvent accountEvent = new AccountEvent();
                    accountEvent.setEventType(EventType.AE_NET_REGISTER);
                    if (code.equals(ServerCode.S_OK.name())) {
                        accountEvent.setEventCode(EventCode.S_OK);
                        accountEvent.setServerCode(ServerCode.S_OK);
                        EventBus.getDefault().post(accountEvent);
                    } else {
                        accountEvent.setEventCode(EventCode.FA_SERVER_ERROR);
                        accountEvent.setServerCode(ServerCode.valueOf(code));
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
