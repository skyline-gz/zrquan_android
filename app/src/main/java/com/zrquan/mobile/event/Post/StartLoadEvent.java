package com.zrquan.mobile.event.Post;

import com.zrquan.mobile.model.PostFeed;

import java.util.List;

public class StartLoadEvent {
    private List<PostFeed> initialList;
    private Integer[] postIds;

    public StartLoadEvent(Integer[] postIds, List<PostFeed> initialList) {
        this.initialList = initialList;
        this.postIds = postIds;
    }

    public List<PostFeed> getInitialList() {
        return initialList;
    }

    public void setInitialList(List<PostFeed> initialList) {
        this.initialList = initialList;
    }

    public Integer[] getPostIds() {
        return postIds;
    }

    public void setPostIds(Integer[] postIds) {
        this.postIds = postIds;
    }
}
