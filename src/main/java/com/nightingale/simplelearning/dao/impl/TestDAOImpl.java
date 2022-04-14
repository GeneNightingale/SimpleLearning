package com.nightingale.simplelearning.dao.impl;

import com.nightingale.simplelearning.dao.CourseDAO;
import com.nightingale.simplelearning.dao.QuestionDAO;
import com.nightingale.simplelearning.dao.TestDAO;
import com.nightingale.simplelearning.dao.mapper.CourseListRowMapper;
import com.nightingale.simplelearning.dao.mapper.TestRowMapper;
import com.nightingale.simplelearning.model.Course;
import com.nightingale.simplelearning.model.Question;
import com.nightingale.simplelearning.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class TestDAOImpl implements TestDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private TestRowMapper testRowMapper;

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private CourseListRowMapper courseListRowMapper;

    private static final Logger LOGGER = Logger.getLogger(TestDAOImpl.class.getName());

    @Override
    public Test getTestById(BigInteger id) {
        try {
            Test test = jdbcTemplate.queryForObject(SELECT_TEST_BY_ID, testRowMapper, id);
            List<Question> questions = questionDAO.getAllQuestionsByTestId(id);
            test.setQuestions(questions);
            return test;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Test> getAllTestsByCourseId(BigInteger courseId) {
        try {
            return jdbcTemplate.query(SELECT_TESTS_BY_COURSE_ID, testRowMapper, courseId);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Test> getAllTests() {
        try {
            return jdbcTemplate.query(SELECT_ALL_TESTS, testRowMapper);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean deleteAllTestsByCourseId(BigInteger id) {
        try {
            Course course = jdbcTemplate.queryForObject(courseDAO.SELECT_COURSE_BY_ID, courseListRowMapper, id);
            if (course!=null) {
                jdbcTemplate.update(DELETE_TESTS_BY_COURSE_ID, id);
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

    @Override
    @Transactional(rollbackFor = {DataAccessException.class})
    public boolean save(Test test, BigInteger courseId) {
        if(isInvalidTest(test)){
            LOGGER.log(Level.WARNING, "Invalid Test");
            return false;
        }
        try {
            jdbcTemplate.update(INSERT_TEST_TO_COURSE, test.getTitle(), test.getTime(), courseId);
            return true;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean delete(BigInteger id) {
        try {
            //TRY TO DELETE TEST'S QUESTIONS FIRST
            boolean questionDeletionSuccessfulResult = questionDAO.deleteAllQuestionsByTestId(id);
            //IF THAT'S DONE, TRY TO DELETE TEST ITSELF
            if (questionDeletionSuccessfulResult) {
                jdbcTemplate.update(DELETE_TEST_BY_ID, id);
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
    public boolean update(BigInteger id, Test newTest) {
        if(isInvalidTest(newTest)){
            LOGGER.log(Level.WARNING, "Invalid Test");
            return false;
        }
        try {
            Test test = jdbcTemplate.queryForObject(SELECT_TEST_BY_ID, testRowMapper, id);
            if (test!=null) {
                jdbcTemplate.update(UPDATE_TEST_BY_ID, newTest.getTitle(), newTest.getTime(), id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Test with given ID");
                return false;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    private boolean isInvalidTest(Test test) {
        if (test == null)
            return true;
        else
            return test.getTitle() == null || test.getTime() < 1;
    }

}


