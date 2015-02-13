package com.zrquan.mobile.event.Discussion;

import com.zrquan.mobile.model.DiscussionFeed;

import java.util.List;

public class PullUpEvent {
    private List<DiscussionFeed> partialList;

    public PullUpEvent(List<DiscussionFeed> partialList) {
        this.partialList = partialList;
    }

    public List<DiscussionFeed> getPartialList() {
        return partialList;
    }

    public void setPartialList(List<DiscussionFeed> partialList) {
        this.partialList = partialList;
    }

}
