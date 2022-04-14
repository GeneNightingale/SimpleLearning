package com.nightingale.simplelearning.dao.impl;

import com.nightingale.simplelearning.dao.*;
import com.nightingale.simplelearning.dao.mapper.CourseListRowMapper;
import com.nightingale.simplelearning.dao.mapper.UserRowMapper;
import com.nightingale.simplelearning.model.*;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;

@Repository
public class CourseDAOImpl implements CourseDAO{

    private static final Logger LOGGER = Logger.getLogger(CourseDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MaterialDAO materialDAO;

    @Autowired
    private LectureDAO lectureDAO;

    @Autowired
    private TestDAO testDAO;

    @Autowired
    private AppealDAO appealDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRowMapper userRowMapper;

    @Autowired
    private CourseListRowMapper courseListRowMapper;

    @Override
    public Course getCourseById(BigInteger id) {
        try {
            Course course = jdbcTemplate.queryForObject(SELECT_COURSE_BY_ID, courseListRowMapper, id);
            if (course != null) {
                List<Material> materials = materialDAO.getAllMaterialsByCourseId(id);
                course.setMaterials(materials);
                List<Lecture> lectures = lectureDAO.getAllLecturesByCourseId(id);
                course.setLectures(lectures);
                List<Test> tests = testDAO.getAllTestsByCourseId(id);
                course.setTests(tests);
                List<User> students = getAllStudentsByCourseId(id);
                course.setStudents(students);
                //course.setTeacher(userDAO.getUserById());
                //TODO: ADD APPEALS (?)
                //List<Appeal> appeals = testDAO.getAllAppealsByCourseId(id);
                //                course.setAppeals(appeals);
                return course;
            } else
                return null;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Course getCourseByTitle(String title) {
        try {
            Course course = jdbcTemplate.queryForObject(SELECT_COURSE_BY_TITLE, courseListRowMapper, title);
            if (course != null) {
                BigInteger id = BigInteger.valueOf(course.getCourseId());
                List<Material> materials = materialDAO.getAllMaterialsByCourseId(id);
                course.setMaterials(materials);
                List<Lecture> lectures = lectureDAO.getAllLecturesByCourseId(id);
                course.setLectures(lectures);
                List<Test> tests = testDAO.getAllTestsByCourseId(id);
                course.setTests(tests);
                List<User> students = getAllStudentsByCourseId(id);
                course.setStudents(students);
                //TODO: ADD APPEALS (?)
                //List<Appeal> appeals = testDAO.getAllAppealsByCourseId(id);
                //                course.setAppeals(appeals);
                return course;
            } else
                return null;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Course> getAllCourses() {
        try {
            return jdbcTemplate.query(SELECT_ALL_COURSES, courseListRowMapper);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Course> getAllCoursesByTeacher(BigInteger id) {
        try {
            return jdbcTemplate.query(SELECT_ALL_COURSES_BY_TEACHER, courseListRowMapper, id);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Course> getAllCoursesByStudent(BigInteger id) {
        try {
            return jdbcTemplate.query(SELECT_ALL_COURSES_BY_STUDENT, courseListRowMapper, id);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<User> getAllStudentsByCourseId(BigInteger id) {
        try {
            Course course = jdbcTemplate.queryForObject(SELECT_COURSE_BY_ID, courseListRowMapper, id);
            if (course!=null) {
                return jdbcTemplate.query(SELECT_ALL_STUDENTS_BY_COURSE_ID, userRowMapper, id);
            } else {
                LOGGER.log(Level.WARNING, "No Course with given ID");
                return null;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }
    }

    @Override
    @Transactional
    public boolean save(Course course) {
        try {
            jdbcTemplate.update(INSERT_COURSE, course.getTitle(), course.getTeacher().getUserId(), course.getDescription());
            return true;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(BigInteger id) {
        try {
            //TRY TO DELETE MATERIALS, LECTURES, TESTS AND MAYBE APPEALS FIRST
            boolean materialDeletionSuccessfulResult = materialDAO.deleteAllMaterialsByCourseId(id);
            boolean lectureDeletionSuccessfulResult = lectureDAO.deleteAllLecturesByCourseId(id);
            boolean testDeletionSuccessfulResult = testDAO.deleteAllTestsByCourseId(id);
            boolean appealDeletionSuccessfulResult = appealDAO.deleteAllAppealsByCourseId(id);

            //IF THAT'S DONE, TRY TO DELETE Course ITSELF
            if (materialDeletionSuccessfulResult && lectureDeletionSuccessfulResult &&
                testDeletionSuccessfulResult && appealDeletionSuccessfulResult) {
                jdbcTemplate.update(DELETE_COURSE_BY_ID, id);
                return true;
            } else
                return false;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean update(BigInteger id, Course newCourse) {
        try {
            Course course = jdbcTemplate.queryForObject(SELECT_COURSE_BY_ID, courseListRowMapper, id);
            if (course!=null) {
                jdbcTemplate.update(UPDATE_COURSE_BY_ID, newCourse.getTitle(), newCourse.getDescription(), id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Course with given ID");
                return false;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }
}