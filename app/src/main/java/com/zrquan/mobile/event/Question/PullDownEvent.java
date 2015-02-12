package com.zrquan.mobile.event.Question;

import com.zrquan.mobile.model.Discussion;
import com.zrquan.mobile.model.Question;

import java.util.List;

public class PullDownEvent {
    private List<Question> initialList;
    private Integer[] questionIds;

    public PullDownEvent(Integer[] questionIds, List<Question> initialList) {
        this.initialList = initialList;
        this.questionIds = questionIds;
    }

    public List<Question> getInitialList() {
        return initialList;
    }

    public void setInitialList(List<Question> initialList) {
        this.initialList = initialList;
    }

    public Integer[] getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(Integer[] questionIds) {
        this.questionIds = questionIds;
    }
}
