package com.zrquan.mobile.model;

import java.sql.Date;

/**
 * Created by James_Ouyang on 2015/2/6.
 */
public class Post {
    private String content;
    private int anonymousFlag;
    private Date postCreatedAt;
    private int userId;

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

    public Date getPostCreatedAt() {
        return postCreatedAt;
    }

    public void setPostCreatedAt(Date postCreatedAt) {
        this.postCreatedAt = postCreatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
