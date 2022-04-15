package com.nightingale.simplelearning.model;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class Result {

    private long resultId;

    private BigDecimal score;

    private long studentId;

    private long testId;

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

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
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
                Objects.equals(getTestId(), result.getTestId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResultId(), getScore(), getStudentId(), getTestId());
    }
}

