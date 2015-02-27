package com.zrquan.mobile.support.enums;

/**
 * Created by James_Ouyang on 2015/2/27.
 */
public enum ListItemCounter {
    POST_FEED_COUNT_PER_PAGE(100);

    private int count;

    private ListItemCounter(int count) {
        this.count = count;
    }

    public int count() {
        return count;
    }

}
