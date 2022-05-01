package com.nightingale.simplelearning.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {

    private long courseId;

    @NotNull
    //@NotEmpty
    //@Size(min = 4, max = 23)
    private String title;

    //@NotNull
    private long teacherId;

    private String description;

    private List<User> students;

    private List<Material> materials;

    private List<Lecture> lectures;

    private List<Test> tests;

    private List<Appeal> appeals;

    private boolean isPublic;

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<Appeal> getAppeals() {
        return appeals;
    }

    public void setAppeals(List<Appeal> appeals) {
        this.appeals = appeals;
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
        Course course = (Course) o;
        return Objects.equals(getCourseId(), course.getCourseId())
                && Objects.equals(getTitle(), course.getTitle())
                && Objects.equals(getTeacherId(), course.getTeacherId())
                && Objects.equals(getDescription(), course.getDescription())
                && Objects.equals(getStudents(), course.getStudents())
                && Objects.equals(getMaterials(), course.getMaterials())
                && Objects.equals(getLectures(), course.getLectures())
                && Objects.equals(getTests(), course.getTests())
                && Objects.equals(getAppeals(), course.getAppeals())
                && Objects.equals(isPublic(), course.isPublic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseId(), getTitle(), getTeacherId(), getDescription(),
                getStudents(), getMaterials(), getLectures(), getTests(), getAppeals(), isPublic());
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + courseId +
                ", title='" + title +
                ", teacherId=" + teacherId +
                ", description=" + description +
                ", students=" + students +
                ", materials=" + materials +
                ", lectures=" + lectures +
                ", tests=" + tests +
                ", appeals=" + appeals +
                ", isPublic=" + isPublic +
                '}';
    }
}