package com.nightingale.simplelearning.model;

import java.util.Objects;

public class Answer {

    private long answerId;

    private String answer;

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Answer answer = (Answer) o;
        return Objects.equals(getAnswerId(), answer.getAnswerId()) &&
                Objects.equals(getAnswer(), answer.getAnswer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAnswerId(), getAnswer());
    }
}


