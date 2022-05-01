package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.dao.CourseDAO;
import com.nightingale.simplelearning.model.Course;
import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.service.CourseService;
import com.nightingale.simplelearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private UserService userService;

    @Override
    public Course getCourseById(BigInteger id) {
        User currentUser = userService.getCurrentUser();
        switch (currentUser.getRole().toString()) {
            case "STUDENT":
                return getCourseByIdStudent(id);
            case "TEACHER":
                return courseDAO.getCourseById(id);
            default:
                return null;
        }
    }

    @Override
    public Course getCourseByIdStudent(BigInteger id) {
        return courseDAO.getCourseByIdStudent(id);
    }

    @Override
    public Course getCourseByTitle(String title) {
        return courseDAO.getCourseByTitle(title);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    @Override
    public List<Course> getCoursesByCurrentUser() {
        User currentUser = userService.getCurrentUser();
        switch (currentUser.getRole().toString()) {
            case "STUDENT":
                return getAllCoursesByStudent(BigInteger.valueOf(currentUser.getUserId()));
            case "TEACHER":
                return getAllCoursesByTeacher(BigInteger.valueOf(currentUser.getUserId()));
            default:
                return null;
        }
    }

    @Override
    public List<Course> getAllCoursesByTeacher(BigInteger id) {
        return courseDAO.getAllCoursesByTeacher(id);
    }

    @Override
    public List<Course> getAllCoursesByStudent(BigInteger id) {
        return courseDAO.getAllCoursesByStudent(id);
    }

    @Override
    public List<User> getAllStudentsByCourseId(BigInteger id) {
        List<User> students = courseDAO.getAllStudentsByCourseId(id);
        for (User student: students) {
            student.setPassword("");
        }
        return students;
    }

    @Override
    public List<User> getAllNotStudentsByCourseId(BigInteger id) {
        List<User> students = courseDAO.getAllNotStudentsByCourseId(id);
        for (User student: students) {
            student.setPassword("");
        }
        return students;
    }

    @Override
    public boolean saveCourseAsCurrentTeacher(Course course) {
        User currentUser = userService.getCurrentUser();
        if ("TEACHER".equals(currentUser.getRole().toString())) {
            course.setTeacherId(currentUser.getUserId());
            return save(course);
        } else {
            return false;
        }
    }

    @Override
    public boolean addCourseMember(BigInteger courseId, BigInteger studentId) {
        return courseDAO.addCourseMember(courseId, studentId);
    }

    @Override
    public boolean removeCourseMember(BigInteger courseId, BigInteger studentId) {
        return courseDAO.removeCourseMember(courseId, studentId);
    }

    @Override
    public boolean makePublic(BigInteger courseId) {
        return courseDAO.makePublic(courseId);
    }

    @Override
    public boolean makePrivate(BigInteger courseId) {
        return courseDAO.makePrivate(courseId);
    }

    @Override
    public boolean save(Course course) {
        return courseDAO.save(course);
    }

    @Override
    public boolean delete(BigInteger id) {
        return courseDAO.delete(id);
    }

    @Override
    public boolean update(BigInteger id, Course newCourse) {
        return courseDAO.update(id, newCourse);
    }
}
