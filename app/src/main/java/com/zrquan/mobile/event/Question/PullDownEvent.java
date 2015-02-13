package com.zrquan.mobile.event.Question;

import com.zrquan.mobile.model.QuestionFeed;

import java.util.List;

public class PullDownEvent {
    private List<QuestionFeed> initialList;
    private Integer[] questionIds;

    public PullDownEvent(Integer[] questionIds, List<QuestionFeed> initialList) {
        this.initialList = initialList;
        this.questionIds = questionIds;
    }

    public List<QuestionFeed> getInitialList() {
        return initialList;
    }

    public void setInitialList(List<QuestionFeed> initialList) {
        this.initialList = initialList;
    }

    public Integer[] getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(Integer[] questionIds) {
        this.questionIds = questionIds;
    }
}
