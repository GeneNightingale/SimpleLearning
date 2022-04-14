package com.nightingale.simplelearning.model;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class Result {

    private long resultId;

    private BigDecimal score;

    private User student;

    private Test test;

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

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Result result = (Result) o;
        return Objects.equals(getResultId(), result.getResultId()) &&
                Objects.equals(getScore(), result.getScore()) &&
                Objects.equals(getStudent(), result.getStudent()) &&
                Objects.equals(getTest(), result.getTest());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResultId(), getScore(), getStudent(), getTest());
    }
}

