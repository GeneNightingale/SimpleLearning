package com.nightingale.simplelearning.dao;

import com.nightingale.simplelearning.model.Course;
import com.nightingale.simplelearning.model.User;

import java.math.BigInteger;
import java.util.List;

public interface CourseDAO {

    String SELECT_COURSE_BY_TITLE = "SELECT * FROM course WHERE course.title = ?;";

    String SELECT_COURSE_BY_ID = "SELECT * FROM course WHERE courseId = ?;";

    String SELECT_COURSE_BY_ID_STUDENT = "SELECT * FROM course WHERE isPublic = 'true' AND courseId = ?;";

    String SELECT_ALL_COURSES = "SELECT * FROM course;";

    //Unsafe, reveals password
    //String GET_ALL_COURSES_BY_TEACHER = "SELECT * FROM `course` join user on " +
    //        "course.teacherId=user.userId where user.userId = ?;";

    String SELECT_ALL_COURSES_BY_TEACHER = "SELECT course.courseId, course.title, course.description, " +
                                        "course.teacherId, course.isPublic, user.name, user.login " +
                                        "FROM course join user " +
                                        "on course.teacherId=user.userId where user.userId = ?";

    //Just the course
    //String GET_ALL_COURSES_BY_TEACHER = "SELECT * FROM course where course.teacherId = ?";

    String SELECT_ALL_COURSES_BY_STUDENT = "SELECT course.courseId, course.title, course.description, course.teacherId, " +
            "course.isPublic FROM `course` join coursemembership " +
            "on course.courseId=coursemembership.courseId where isPublic = 'true' AND coursemembership.studentId = ?";

    //Implement if appeals go through
    String SELECT_UNAVAILABLE_COURSES_BY_STUDENT = "SELECT course.courseId, course.title, course.description, course.teacherId, " +
            "course.isPublic FROM `course` join coursemembership " +
            "on course.courseId=coursemembership.courseId where coursemembership.studentId <> ?";

    String SELECT_ALL_STUDENTS_BY_COURSE_ID = "SELECT user.userId, user.name, user.login, user.password, role.roleName  " +
            "FROM user join coursemembership " +
            "on user.userId=coursemembership.studentId " +
            "JOIN role on user.roleId = role.roleId " +
            "where coursemembership.courseId = ?;";

    String SELECT_ALL_NOT_STUDENTS_BY_COURSE_ID = "SELECT user.userId, user.name, user.login, user.password, role.roleName " +
            "FROM user JOIN role on user.roleId = role.roleId" +
            "    WHERE user.userId NOT IN ( " +
            " SELECT user.userId " +
            "            FROM user join coursemembership " +
            "            on user.userId=coursemembership.studentId " +
            "            JOIN role on user.roleId = role.roleId " +
            "            where coursemembership.courseId = ? ) " +
            "    AND user.roleId = 3";

    String SELECT_TEACHER_BY_COURSE_ID = "SELECT user.userId, user.login, user.name, user.password, role.roleName " +
            "FROM user join course " +
            "on course.teacherId= user.userId " +
            "JOIN role on role.roleId = user.roleId " +
            "WHERE course.courseId = ?;";

    //Redundant perhaps
    //String GET_ALL_COURSES_BY_STUDENT = "SELECT * FROM `course` join coursemembership " +
    //        "on course.courseId=coursemembership.courseId where coursemembership.studentId = ?;";


    String ADD_COURSE_MEMBER = "INSERT INTO coursemembership (courseId, studentId) VALUES (?, ?)";

    String REMOVE_COURSE_MEMBER = "DELETE FROM coursemembership " +
            "WHERE courseId = ? AND studentId = ?";

    String REMOVE_COURSE_MEMBERS_RESULTS = "DELETE FROM result " +
            "WHERE userId = ?";

    String INSERT_COURSE= "INSERT INTO COURSE (title, teacherId, description) " +
            "VALUES (?, ?, ?)";

    String DELETE_COURSE_BY_ID= "DELETE FROM course WHERE courseId = ?;";

    String CLEAN_UP_MEMBERS = "DELETE FROM coursemembership WHERE courseId = ?;";

    String MAKE_PUBLIC = "UPDATE course SET isPublic = 'true' WHERE courseId = ?;";

    String MAKE_PRIVATE = "UPDATE course SET isPublic = 'false' WHERE courseId = ?;";

    String UPDATE_COURSE_BY_ID= "UPDATE course " +
            "SET title = ?, description = ? " +
            "WHERE courseId = ?;";

    public Course getCourseById(BigInteger id);
    public Course getCourseByIdStudent(BigInteger id);
    public Course getCourseByTitle(String title);
    public List<Course> getAllCourses();
    public List<Course> getAllCoursesByTeacher(BigInteger id);
    public List<Course> getAllCoursesByStudent(BigInteger id);
    public List<User> getAllStudentsByCourseId(BigInteger id);
    public List<User> getAllNotStudentsByCourseId(BigInteger id);
    public User getTeacherByCourseId(BigInteger id);
    boolean addCourseMember(BigInteger courseId, BigInteger studentId);
    boolean removeCourseMember(BigInteger courseId, BigInteger studentId);
    boolean makePublic(BigInteger courseId);
    boolean makePrivate(BigInteger courseId);
    boolean save(Course course);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Course newCourse);
}
