package com.nightingale.simplelearning.model;

import java.util.List;
import java.util.Objects;

public class Test {

    private long testId;

    private String title;

    private int time;

    private List<Question> questions;

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Test test = (Test) o;
        return Objects.equals(getTestId(), test.getTestId()) &&
                Objects.equals(getTitle(), test.getTitle()) &&
                Objects.equals(getTime(), test.getTime()) &&
                Objects.equals(getQuestions(), test.getQuestions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTestId(), getTitle(), getTime(), getQuestions());
    }
}

