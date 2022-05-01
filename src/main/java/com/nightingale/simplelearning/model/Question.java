package com.nightingale.simplelearning.model;

import java.util.List;
import java.util.Objects;

public class Question {

    private long questionId;

    private long questionNum;

    private String text;

    private String answer;

    private List<Answer> answers;

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(long questionNum) {
        this.questionNum = questionNum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Question question = (Question) o;
        return Objects.equals(getQuestionId(), question.getQuestionId()) &&
                Objects.equals(getQuestionNum(), question.getQuestionNum()) &&
                Objects.equals(getText(), question.getText()) &&
                Objects.equals(getAnswer(), question.getAnswer()) &&
                Objects.equals(getAnswers(), question.getAnswers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestionId(), getQuestionNum(), getText(), getAnswer(), getAnswers());
    }
}

