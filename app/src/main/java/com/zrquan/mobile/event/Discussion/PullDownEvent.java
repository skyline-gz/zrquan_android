package com.zrquan.mobile.event.Discussion;

import com.zrquan.mobile.model.Discussion;

import java.util.List;

public class PullDownEvent {
    private List<Discussion> initialList;
    private Integer[] discussionIds;

    public PullDownEvent(Integer[] discussionIds, List<Discussion> initialList) {
        this.initialList = initialList;
        this.discussionIds = discussionIds;
    }

    public List<Discussion> getInitialList() {
        return initialList;
    }

    public void setInitialList(List<Discussion> initialList) {
        this.initialList = initialList;
    }

    public Integer[] getDiscussionIds() {
        return discussionIds;
    }

    public void setDiscussionIds(Integer[] discussionIds) {
        this.discussionIds = discussionIds;
    }
}
