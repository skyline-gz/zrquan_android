package com.zrquan.mobile.event;

import com.zrquan.mobile.model.Discussion;

import java.util.List;

public class DiscussionPullUpEvent {
    private List<Discussion> partialList;

    public DiscussionPullUpEvent(List<Discussion> partialList) {
        this.partialList = partialList;
    }

    public List<Discussion> getPartialList() {
        return partialList;
    }

    public void setPartialList(List<Discussion> partialList) {
        this.partialList = partialList;
    }

}
