package com.nightingale.simplelearning.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public class Test {

    private long testId;

    private String title;

    private int time;

    private long courseId;

    private List<Question> questions;

    public boolean isPublic;

    public boolean passed;

    public BigDecimal grade;

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

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
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
                Objects.equals(getCourseId(), test.getCourseId()) &&
                Objects.equals(getQuestions(), test.getQuestions()) &&
                Objects.equals(isPublic(), test.isPublic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTestId(), getTitle(), getTime(), getCourseId(), getQuestions(), isPublic());
    }
}

