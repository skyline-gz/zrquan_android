package com.zrquan.mobile.model;

import android.text.TextUtils;

public class Account{

    private String phoneNum;
    private String accessToken;

    private Boolean verified;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String access_token) {
        this.accessToken = accessToken;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(this.accessToken) && !TextUtils.isEmpty(this.phoneNum);
    }

    public boolean isLogin() {
        return isValid() && verified;
    }
}
