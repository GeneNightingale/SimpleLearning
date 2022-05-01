package com.nightingale.simplelearning.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public class Lecture {

    private long lectureId;

    private String title;

    private List<Page> pages;

    private long courseId;

    private boolean isPublic;

    public long getLectureId() {
        return lectureId;
    }

    public void setLectureId(long lectureId) {
        this.lectureId = lectureId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Lecture lecture = (Lecture) o;
        return Objects.equals(getLectureId(), lecture.getLectureId()) &&
                Objects.equals(getTitle(), lecture.getTitle()) &&
                Objects.equals(getPages(), lecture.getPages()) &&
                Objects.equals(getCourseId(), lecture.getCourseId()) &&
                Objects.equals(isPublic(), lecture.isPublic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLectureId(), getTitle(), getPages(), getCourseId(), isPublic());
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "id=" + lectureId +
                ", title='" + title +
                ", pages=" + pages +
                ", courseId=" + courseId +
                ", isPublic=" + isPublic +
                '}';
    }
}
