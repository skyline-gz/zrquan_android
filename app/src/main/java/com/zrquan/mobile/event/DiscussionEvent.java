package com.zrquan.mobile.event;

import com.zrquan.mobile.model.Discussion;

import java.util.List;

public class DiscussionEvent {
    public List<Discussion> discussionList;

    public DiscussionEvent(List<Discussion> discussionList) {
        this.discussionList = discussionList;
    }
}
