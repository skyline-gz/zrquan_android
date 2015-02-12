package com.zrquan.mobile.event.Discussion;

import com.zrquan.mobile.model.Discussion;

import java.util.List;

public class PullUpEvent {
    private List<Discussion> partialList;

    public PullUpEvent(List<Discussion> partialList) {
        this.partialList = partialList;
    }

    public List<Discussion> getPartialList() {
        return partialList;
    }

    public void setPartialList(List<Discussion> partialList) {
        this.partialList = partialList;
    }

}
