package com.zrquan.mobile.model;

import android.text.TextUtils;

public class Account{

    private String mobile;
    private String accessToken;
    private Boolean verified;
    private String avatar;
    private int gender;
    private int id;
    private String name;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(this.accessToken) && !TextUtils.isEmpty(this.mobile);
    }

    public boolean isLogin() {
        return isValid() && verified;
    }
}
