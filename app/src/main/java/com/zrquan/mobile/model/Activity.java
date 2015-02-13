package com.zrquan.mobile.model;

import java.sql.Date;

/**
 * Created by James_Ouyang on 2015/2/13.
 */
public class Activity {
    private String activityType;
    private Date created_at;
    private String userName;
    private String userProp1;
    private String userProp2;
    private String avatar;
    private String mainContent;
    private String count1;
    private String count2;
    private String subContentAgree;
    private String subContent;

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProp1() {
        return userProp1;
    }

    public void setUserProp1(String userProp1) {
        this.userProp1 = userProp1;
    }

    public String getUserProp2() {
        return userProp2;
    }

    public void setUserProp2(String userProp2) {
        this.userProp2 = userProp2;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    public String getCount1() {
        return count1;
    }

    public void setCount1(String count1) {
        this.count1 = count1;
    }

    public String getCount2() {
        return count2;
    }

    public void setCount2(String count2) {
        this.count2 = count2;
    }

    public String getSubContentAgree() {
        return subContentAgree;
    }

    public void setSubContentAgree(String subContentAgree) {
        this.subContentAgree = subContentAgree;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }
}
