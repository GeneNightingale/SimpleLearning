package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.model.Course;
import com.nightingale.simplelearning.model.User;

import java.math.BigInteger;
import java.util.List;

public interface CourseService {
    public Course getCourseById(BigInteger id);
    public Course getCourseByIdStudent(BigInteger id);
    public Course getCourseByTitle(String title);
    public List<Course> getAllCourses();
    public List<Course> getCoursesByCurrentUser();
    public List<Course> getAllCoursesByTeacher(BigInteger id);
    public List<Course> getAllCoursesByStudent(BigInteger id);
    //public List<Course> getAllUnavailableCoursesByStudent(BigInteger id); //TODO: Implement if appeals go through
    public List<User> getAllStudentsByCourseId(BigInteger id);
    public List<User> getAllNotStudentsByCourseId(BigInteger id);
    boolean saveCourseAsCurrentTeacher(Course course);
    boolean addCourseMember(BigInteger courseId, BigInteger studentId);
    boolean removeCourseMember(BigInteger courseId, BigInteger studentId);
    boolean makePublic(BigInteger courseId);
    boolean makePrivate(BigInteger courseId);
    boolean save(Course course);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Course newCourse);
}
