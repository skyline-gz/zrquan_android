package com.zrquan.mobile.event.Discussion;

import com.zrquan.mobile.model.DiscussionFeed;

import java.util.List;

public class PullDownEvent {
    private List<DiscussionFeed> initialList;
    private Integer[] discussionIds;

    public PullDownEvent(Integer[] discussionIds, List<DiscussionFeed> initialList) {
        this.initialList = initialList;
        this.discussionIds = discussionIds;
    }

    public List<DiscussionFeed> getInitialList() {
        return initialList;
    }

    public void setInitialList(List<DiscussionFeed> initialList) {
        this.initialList = initialList;
    }

    public Integer[] getDiscussionIds() {
        return discussionIds;
    }

    public void setDiscussionIds(Integer[] discussionIds) {
        this.discussionIds = discussionIds;
    }
}
