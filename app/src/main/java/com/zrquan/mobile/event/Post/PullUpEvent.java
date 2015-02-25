package com.zrquan.mobile.event.Post;

import com.zrquan.mobile.model.PostFeed;

import java.util.List;

public class PullUpEvent {
    private List<PostFeed> partialList;

    public PullUpEvent(List<PostFeed> partialList) {
        this.partialList = partialList;
    }

    public List<PostFeed> getPartialList() {
        return partialList;
    }

    public void setPartialList(List<PostFeed> partialList) {
        this.partialList = partialList;
    }

}
