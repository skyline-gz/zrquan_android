package com.zrquan.mobile.event.Activity;

import com.zrquan.mobile.model.Activity;

import java.util.List;

public class PullDownEvent {
    private List<Activity> initialList;
    private Integer[] activityIds;

    public PullDownEvent(Integer[] activityIds, List<Activity> initialList) {
        this.initialList = initialList;
        this.activityIds = activityIds;
    }

    public List<Activity> getInitialList() {
        return initialList;
    }

    public void setInitialList(List<Activity> initialList) {
        this.initialList = initialList;
    }

    public Integer[] getActivityIds() {
        return activityIds;
    }

    public void setActivityIds(Integer[] activityIds) {
        this.activityIds = activityIds;
    }
}
