package com.zrquan.mobile.event;

import com.zrquan.mobile.model.Discussion;

import java.util.List;

public class PullDownEvent {
    private List<Discussion> initialDiscussionList;
    private Integer[] discussionIds;

    public PullDownEvent(Integer[] discussionIds, List<Discussion> initialList) {
        this.initialDiscussionList = initialList;
        this.discussionIds = discussionIds;
    }

    public List<Discussion> getInitialDiscussionList() {
        return initialDiscussionList;
    }

    public void setInitialDiscussionList(List<Discussion> initialDiscussionList) {
        this.initialDiscussionList = initialDiscussionList;
    }

    public Integer[] getDiscussionIds() {
        return discussionIds;
    }

    public void setDiscussionIds(Integer[] discussionIds) {
        this.discussionIds = discussionIds;
    }
}
