package com.nightingale.simplelearning.model;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public class Result {

    private long resultId;

    private BigDecimal score;

    private long studentId;

    private String name;

    private long testId;

    private String title;

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Result result = (Result) o;
        return Objects.equals(getResultId(), result.getResultId()) &&
                Objects.equals(getScore(), result.getScore()) &&
                Objects.equals(getStudentId(), result.getStudentId()) &&
                Objects.equals(getName(), result.getName()) &&
                Objects.equals(getTestId(), result.getTestId()) &&
                Objects.equals(getTitle(), result.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResultId(), getScore(), getStudentId(), getName(), getTestId(), getTitle());
    }
}

