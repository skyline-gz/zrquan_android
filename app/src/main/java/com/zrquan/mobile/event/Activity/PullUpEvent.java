package com.zrquan.mobile.event.Activity;

import com.zrquan.mobile.model.Activity;
import com.zrquan.mobile.model.Discussion;

import java.util.List;

public class PullUpEvent {
    private List<Activity> partialList;

    public PullUpEvent(List<Activity> partialList) {
        this.partialList = partialList;
    }

    public List<Activity> getPartialList() {
        return partialList;
    }

    public void setPartialList(List<Activity> partialList) {
        this.partialList = partialList;
    }

}
