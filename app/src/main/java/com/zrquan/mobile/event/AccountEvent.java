package com.zrquan.mobile.event;

import org.json.JSONObject;

public class AccountEvent extends BaseEvent{

    private String verifyCode;

    private String token;

    private JSONObject userInfo;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JSONObject getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(JSONObject userInfo) {
        this.userInfo = userInfo;
    }
}
