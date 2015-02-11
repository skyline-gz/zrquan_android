package com.zrquan.mobile.controller;

import android.content.Context;

import com.android.volley.VolleyError;
import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.dao.AccountDao;
import com.zrquan.mobile.event.AccountEvent;
import com.zrquan.mobile.model.Account;
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

    public static void sendVerifyCode(String phoneNum) {
        sendVerifyCode(phoneNum, false);
    }

    //发送验证码
    public static void sendVerifyCode(String phoneNum, boolean ignoreMobileCheck) {
        // pass second argument as "null" for GET requests
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phoneNum);
        if(ignoreMobileCheck) {
            params.put("ignore_mobile_check", "true");
        }
        String url = UrlUtils.getUrlWithParams("users/send_verify_code", params);
        VolleyJsonRequest.get(url, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String code = response.getString("code");
                    AccountEvent accountEvent = new AccountEvent();
                    accountEvent.setEventType(EventType.AE_NET_SEND_VERIFY_CODE);
                    if(code.equals(ServerCode.S_OK.name())) {
                        accountEvent.setEventCode(EventCode.S_OK);
                        accountEvent.setServerCode(ServerCode.S_OK);
                        JSONObject results = response.getJSONObject("results");
                        String verifyCode = results.getString("verify_code");
                        accountEvent.setVerifyCode(verifyCode);
                    } else {
                        accountEvent.setEventCode(EventCode.FA_SERVER_ERROR);
                        accountEvent.setServerCode(ServerCode.valueOf(code));
                    }
                    EventBus.getDefault().post(accountEvent);
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

    //用户注册
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
                    String code = response.getString("code");
                    AccountEvent accountEvent = new AccountEvent();
                    accountEvent.setEventType(EventType.AE_NET_REGISTER);
                    if (code.equals(ServerCode.S_OK.name())) {
                        accountEvent.setEventCode(EventCode.S_OK);
                        accountEvent.setServerCode(ServerCode.S_OK);
                    } else {
                        accountEvent.setEventCode(EventCode.FA_SERVER_ERROR);
                        accountEvent.setServerCode(ServerCode.valueOf(code));
                    }
                    EventBus.getDefault().post(accountEvent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    //用户登陆
    public static void loginAccount(String mobile, String password) {
        String url = UrlUtils.getUrl("users/session");

        JSONObject params = new JSONObject();
        try {
            params.put("mobile", mobile);
            params.put("password", password);
        } catch (JSONException e) {
            LogUtils.d("ParseJsonError:", e);
        }

        VolleyJsonRequest.post(url, params, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String code = response.getString("code");
                    AccountEvent accountEvent = new AccountEvent();
                    accountEvent.setEventType(EventType.AE_NET_LOGIN);
                    if (code.equals(ServerCode.S_OK.name())) {
                        accountEvent.setEventCode(EventCode.S_OK);
                        accountEvent.setServerCode(ServerCode.S_OK);
                        JSONObject results = response.getJSONObject("results");
                        String token = results.getString("token");
                        accountEvent.setToken(token);
                    } else {
                        accountEvent.setEventCode(EventCode.FA_SERVER_ERROR);
                        accountEvent.setServerCode(ServerCode.valueOf(code));
                    }
                    EventBus.getDefault().post(accountEvent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    //用户注销账户
    public static void logoutAccount(Context context) {
        Account account = ZrquanApplication.getInstance().getAccount();
        account.setVerified(false);
        account.setAccessToken("");
        ZrquanApplication.getInstance().setAccount(account);
        new AccountDao().saveAccount(context, account);
    }

    public static void resetPassword(String mobile, String newPassword, String verifyCode) {
        String url = UrlUtils.getUrl("users/reset_password");

        JSONObject params = new JSONObject();
        try {
            params.put("mobile", mobile);
            params.put("new_password", newPassword);
            params.put("verify_code", verifyCode);
        } catch (JSONException e) {
            LogUtils.d("ParseJsonError:", e);
        }

        VolleyJsonRequest.post(url, params, new VolleyJsonRequest.ResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String code = response.getString("code");
                    AccountEvent accountEvent = new AccountEvent();
                    accountEvent.setEventType(EventType.AE_NET_RESET_PASSWORD);
                    if (code.equals(ServerCode.S_OK.name())) {
                        accountEvent.setEventCode(EventCode.S_OK);
                        accountEvent.setServerCode(ServerCode.S_OK);
                    } else {
                        accountEvent.setEventCode(EventCode.FA_SERVER_ERROR);
                        accountEvent.setServerCode(ServerCode.valueOf(code));
                    }
                    EventBus.getDefault().post(accountEvent);
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
