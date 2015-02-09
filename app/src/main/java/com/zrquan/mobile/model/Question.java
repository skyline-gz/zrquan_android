package com.zrquan.mobile.model;

import java.sql.Date;

/**
 * Created by James_Ouyang on 2015/2/6.
 */
public class Question {

    private String title;
    private int anonymousFlag;
    private Date createdAt;
    private int answerCount;
    private int answerAgree;
    private int followCount;
    private String userName;
    private String latestCompanyName;
    private String latestPosition;
    private String latestSchoolName;
    private String latestMajor;
    private String avatar;
    private String themeName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAnonymousFlag() {
        return anonymousFlag;
    }

    public void setAnonymousFlag(int anonymousFlag) {
        this.anonymousFlag = anonymousFlag;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getAnswerAgree() {
        return answerAgree;
    }

    public void setAnswerAgree(int answerAgree) {
        this.answerAgree = answerAgree;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
}
