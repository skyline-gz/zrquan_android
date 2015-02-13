package com.zrquan.mobile.model;

import java.sql.Date;

/**
 * Created by James_Ouyang on 2015/2/6.
 */
public class UserSearch {
    private String name;
    private int gender;
    private String latestCompanyName;
    private String latestPosition;
    private String latestSchoolName;
    private String latestMajor;
    private String avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLatestCompanyName() {
        return latestCompanyName;
    }

    public void setLatestCompanyName(String latestCompanyName) {
        this.latestCompanyName = latestCompanyName;
    }

    public String getLatestPosition() {
        return latestPosition;
    }

    public void setLatestPosition(String latestPosition) {
        this.latestPosition = latestPosition;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLatestSchoolName() {
        return latestSchoolName;
    }

    public void setLatestSchoolName(String latestSchoolName) {
        this.latestSchoolName = latestSchoolName;
    }

    public String getLatestMajor() {
        return latestMajor;
    }

    public void setLatestMajor(String latestMajor) {
        this.latestMajor = latestMajor;
    }

}
