package com.zrquan.mobile.model;

import java.sql.Date;

/**
 * Created by James_Ouyang on 2015/2/6.
 */
public class Discussion {
    private String content;
    private int anonymousFlag;
    private Date createdAt;
    private int agreeScore;
    private int commentCount;
    private int commentAgree;
    private String userName;
    private String latestCompanyName;
    private String latestPosition;
    private String latestSchoolName;
    private String latestMajor;
    private String avatar;
    private String themeName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getAgreeScore() {
        return agreeScore;
    }

    public void setAgreeScore(int agreeScore) {
        this.agreeScore = agreeScore;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getCommentAgree() {
        return commentAgree;
    }

    public void setCommentAgree(int commentAgree) {
        this.commentAgree = commentAgree;
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
