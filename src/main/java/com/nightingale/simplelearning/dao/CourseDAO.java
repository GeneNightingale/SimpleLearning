package com.nightingale.simplelearning.dao;

import com.nightingale.simplelearning.model.Course;
import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.model.enums.Role;

import java.math.BigInteger;
import java.util.List;

public interface CourseDAO {

    String SELECT_COURSE_BY_TITLE = "SELECT * FROM course WHERE course.title = ?;";

    String SELECT_COURSE_BY_ID = "SELECT * FROM course WHERE courseId = ?;";

    String SELECT_ALL_COURSES = "SELECT * FROM course;";

    //Unsafe, reveals password
    //String GET_ALL_COURSES_BY_TEACHER = "SELECT * FROM `course` join user on " +
    //        "course.teacherId=user.userId where user.userId = ?;";

    String SELECT_ALL_COURSES_BY_TEACHER = "SELECT course.courseId, course.title, course.description, " +
                                        "user.userId, user.name, user.login " +
                                        "FROM course join user " +
                                        "on course.teacherId=user.userId where user.userId = ?";

    //Just the course
    //String GET_ALL_COURSES_BY_TEACHER = "SELECT * FROM course where course.teacherId = ?";

    String SELECT_ALL_COURSES_BY_STUDENT = "SELECT course.courseId, course.title, course.description, course.teacherId " +
            "FROM `course` join coursemembership " +
            "on course.courseId=coursemembership.courseId where coursemembership.studentId = ?";

    String SELECT_ALL_STUDENTS_BY_COURSE_ID = "SELECT user.userId, user.name, user.login, role.roleName  " +
            "FROM user join coursemembership " +
            "on user.userId=coursemembership.studentId " +
            "JOIN role on user.roleId = role.roleId " +
            "where coursemembership.courseId = ?;";

    //Redundant perhaps
    //String GET_ALL_COURSES_BY_STUDENT = "SELECT * FROM `course` join coursemembership " +
    //        "on course.courseId=coursemembership.courseId where coursemembership.studentId = ?;";

    String INSERT_COURSE= "INSERT INTO COURSE (title, teacherId, description) " +
            "VALUES (?, ?, ?)";

    String DELETE_COURSE_BY_ID= "DELETE FROM course WHERE courseId = ?;";

    String UPDATE_COURSE_BY_ID= "UPDATE course " +
            "SET title = ?, description = ? " +
            "WHERE courseId = ?;";

    public Course getCourseById(BigInteger id);
    public Course getCourseByTitle(String title);
    public List<Course> getAllCourses();
    public List<Course> getAllCoursesByTeacher(BigInteger id);
    public List<Course> getAllCoursesByStudent(BigInteger id);
    public List<User> getAllStudentsByCourseId(BigInteger id);
    boolean save(Course course);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Course newCourse);
}
