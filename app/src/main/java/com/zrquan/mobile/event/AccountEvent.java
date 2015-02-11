package com.zrquan.mobile.event;

public class AccountEvent extends BaseEvent{

    private String verifyCode;

    private String token;

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
}
