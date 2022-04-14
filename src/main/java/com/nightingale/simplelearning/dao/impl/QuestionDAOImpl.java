package com.nightingale.simplelearning.dao.impl;

import com.nightingale.simplelearning.dao.AnswerDAO;
import com.nightingale.simplelearning.dao.QuestionDAO;
import com.nightingale.simplelearning.dao.TestDAO;
import com.nightingale.simplelearning.dao.mapper.QuestionRowMapper;
import com.nightingale.simplelearning.dao.mapper.TestRowMapper;
import com.nightingale.simplelearning.model.Answer;
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
public class QuestionDAOImpl implements QuestionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AnswerDAO answerDAO;

    @Autowired
    private TestDAO testDAO;

    @Autowired
    private TestRowMapper testRowMapper;

    @Autowired
    private QuestionRowMapper questionRowMapper;

    private static final Logger LOGGER = Logger.getLogger(QuestionDAOImpl.class.getName());

    @Override
    public Question getQuestionById(BigInteger id) {
        try {
            Question question = jdbcTemplate.queryForObject(SELECT_QUESTION_BY_ID, questionRowMapper, id);
            List<Answer> answers = answerDAO.getAllAnswersByQuestionId(id);
            question.setAnswers(answers);
            return question;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Question> getAllQuestionsByTestId(BigInteger courseId) {
        try {
            return jdbcTemplate.query(SELECT_QUESTIONS_BY_TEST_ID, questionRowMapper, courseId);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Question> getAllQuestions() {
        try {
            return jdbcTemplate.query(SELECT_ALL_QUESTIONS, questionRowMapper);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public boolean deleteAllQuestionsByTestId(BigInteger id) {
        try {
            //Check if this test exists at all
            Test test = jdbcTemplate.queryForObject(testDAO.SELECT_TEST_BY_ID, testRowMapper, id);
            //If it exists, delete its questions
            if (test!=null) {
                jdbcTemplate.update(DELETE_QUESTIONS_BY_TEST_ID, id);
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

    @Override
    @Transactional(rollbackFor = {DataAccessException.class})
    public boolean save(Question question, BigInteger testId) {
        if(isInvalidQuestion(question)){
            LOGGER.log(Level.WARNING, "Invalid Question");
            return false;
        }
        try {
            jdbcTemplate.update(INSERT_QUESTION_TO_TEST, question.getText(), question.getAnswer(), testId);
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
            //TRY TO DELETE QUESTION'S ANSWERS FIRST
            boolean answerDeletionSuccessfulResult = answerDAO.deleteAllAnswersByQuestionId(id);
            //IF THAT'S DONE, TRY TO DELETE QUESTION ITSELF
            if (answerDeletionSuccessfulResult) {
                jdbcTemplate.update(DELETE_QUESTION_BY_ID, id);
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
    public boolean update(BigInteger id, Question newQuestion) {
        if(isInvalidQuestion(newQuestion)){
            LOGGER.log(Level.WARNING, "Invalid Question");
            return false;
        }
        try {
            Question question = jdbcTemplate.queryForObject(SELECT_QUESTION_BY_ID, questionRowMapper, id);
            if (question!=null) {
                jdbcTemplate.update(UPDATE_QUESTION_BY_ID, newQuestion.getText(), newQuestion.getAnswer(), id);
                //I DONT THINK I NEED TO UPDATE ALL ANSWER IDS FORTUNATELY
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Question with given ID");
                return false;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    private boolean isInvalidQuestion(Question question) {
        if (question == null)
            return true;
        else
            return question.getText() == null || question.getAnswer() == null;
    }

}


