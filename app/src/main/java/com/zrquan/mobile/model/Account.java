package com.zrquan.mobile.model;

import android.text.TextUtils;

public class Account{

    private Integer id;
    private String phoneNum;
    private String accessToken;

    private Boolean verified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
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
