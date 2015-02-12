package com.zrquan.mobile.event.Question;

import com.zrquan.mobile.model.Discussion;
import com.zrquan.mobile.model.Question;

import java.util.List;

public class PullUpEvent {
    private List<Question> partialList;

    public PullUpEvent(List<Question> partialList) {
        this.partialList = partialList;
    }

    public List<Question> getPartialList() {
        return partialList;
    }

    public void setPartialList(List<Question> partialList) {
        this.partialList = partialList;
    }

}
