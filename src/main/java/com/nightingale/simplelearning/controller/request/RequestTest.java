package com.nightingale.simplelearning.controller.request;

import com.nightingale.simplelearning.model.Question;

import java.util.List;

public class RequestTest {

    protected long testId;

    protected List<Question> answers;

    public RequestTest(long testId, List<Question> answers) {
        this.testId = testId;
        this.answers = answers;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public List<Question> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Question> answers) {
        this.answers = answers;
    }
}
