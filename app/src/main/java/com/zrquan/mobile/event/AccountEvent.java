package com.zrquan.mobile.event;

import com.google.gson.JsonObject;

public class AccountEvent extends BaseEvent{

    private String verifyCode;

    private String token;

    private JsonObject userInfo;

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

    public JsonObject getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(JsonObject userInfo) {
        this.userInfo = userInfo;
    }
}
