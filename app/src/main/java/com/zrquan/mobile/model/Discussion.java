package com.zrquan.mobile.model;

import java.sql.Date;

/**
 * Created by James_Ouyang on 2015/2/6.
 */
public class Discussion {
    private String postContent;
    private int postAnonymousFlag;
    private Date postCreatedAt;
    private int postAgreeScore;
    private int commentCount;
    private int commentAgree;
    private String postUserName;
    private String postUserProp1;
    private String postUserProp2;
    private String postUserAvatar;
    private String themeName;
    private String commentContent;
    private Date commentCreatedAt;
    private int commentAgreeScore;
    private String commentUserName;
    private String commentUserProp1;
    private String commentUserProp2;
    private String commentUserAvatar;

    public int getCommentAgreeScore() {
        return commentAgreeScore;
    }

    public void setCommentAgreeScore(int commentAgreeScore) {
        this.commentAgreeScore = commentAgreeScore;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getCommentUserProp1() {
        return commentUserProp1;
    }

    public void setCommentUserProp1(String commentUserProp1) {
        this.commentUserProp1 = commentUserProp1;
    }

    public String getCommentUserProp2() {
        return commentUserProp2;
    }

    public void setCommentUserProp2(String commentUserProp2) {
        this.commentUserProp2 = commentUserProp2;
    }

    public String getCommentUserAvatar() {
        return commentUserAvatar;
    }

    public void setCommentUserAvatar(String commentUserAvatar) {
        this.commentUserAvatar = commentUserAvatar;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public int getPostAnonymousFlag() {
        return postAnonymousFlag;
    }

    public void setPostAnonymousFlag(int postAnonymousFlag) {
        this.postAnonymousFlag = postAnonymousFlag;
    }

    public Date getPostCreatedAt() {
        return postCreatedAt;
    }

    public void setPostCreatedAt(Date postCreatedAt) {
        this.postCreatedAt = postCreatedAt;
    }

    public int getPostAgreeScore() {
        return postAgreeScore;
    }

    public void setPostAgreeScore(int postAgreeScore) {
        this.postAgreeScore = postAgreeScore;
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

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public String getPostUserProp1() {
        return postUserProp1;
    }

    public void setPostUserProp1(String postUserProp1) {
        this.postUserProp1 = postUserProp1;
    }

    public String getPostUserProp2() {
        return postUserProp2;
    }

    public void setPostUserProp2(String postUserProp2) {
        this.postUserProp2 = postUserProp2;
    }

    public String getPostUserAvatar() {
        return postUserAvatar;
    }

    public void setPostUserAvatar(String postUserAvatar) {
        this.postUserAvatar = postUserAvatar;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCommentCreatedAt() {
        return commentCreatedAt;
    }

    public void setCommentCreatedAt(Date commentCreatedAt) {
        this.commentCreatedAt = commentCreatedAt;
    }
}
