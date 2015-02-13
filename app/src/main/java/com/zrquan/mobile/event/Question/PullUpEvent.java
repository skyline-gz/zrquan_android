package com.zrquan.mobile.event.Question;

import com.zrquan.mobile.model.QuestionFeed;

import java.util.List;

public class PullUpEvent {
    private List<QuestionFeed> partialList;

    public PullUpEvent(List<QuestionFeed> partialList) {
        this.partialList = partialList;
    }

    public List<QuestionFeed> getPartialList() {
        return partialList;
    }

    public void setPartialList(List<QuestionFeed> partialList) {
        this.partialList = partialList;
    }

}
