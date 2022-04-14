package com.nightingale.simplelearning.dao.impl;

import com.nightingale.simplelearning.dao.AnswerDAO;
import com.nightingale.simplelearning.dao.QuestionDAO;
import com.nightingale.simplelearning.dao.mapper.AnswerRowMapper;
import com.nightingale.simplelearning.dao.mapper.QuestionRowMapper;
import com.nightingale.simplelearning.model.Answer;
import com.nightingale.simplelearning.model.Question;
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
public class AnswerDAOImpl implements AnswerDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private QuestionRowMapper questionRowMapper;

    @Autowired
    private AnswerRowMapper answerRowMapper;

    private static final Logger LOGGER = Logger.getLogger(AnswerDAOImpl.class.getName());

    @Override
    public Answer getAnswerById(BigInteger id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_ANSWER_BY_ID, answerRowMapper, id);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Answer> getAllAnswersByQuestionId(BigInteger questionId) {
        try {
            return jdbcTemplate.query(SELECT_ANSWERS_BY_QUESTION_ID, answerRowMapper, questionId);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Answer> getAllAnswers() {
        try {
            return jdbcTemplate.query(SELECT_ALL_ANSWERS, answerRowMapper);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public boolean deleteAllAnswersByQuestionId(BigInteger id) {
        try {
            //Check if this question exists at all
            Question question = jdbcTemplate.queryForObject(questionDAO.SELECT_QUESTION_BY_ID, questionRowMapper, id);
            //If it exists, delete its answers
            if (question!=null) {
                jdbcTemplate.update(DELETE_ANSWERS_BY_QUESTION_ID, id);
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

    @Override
    @Transactional(rollbackFor = {DataAccessException.class})
    public boolean save(Answer answer, BigInteger questionId) {
        if(isInvalidAnswer(answer)){
            LOGGER.log(Level.WARNING, "Invalid Answer");
            return false;
        }
        try {
            jdbcTemplate.update(INSERT_ANSWER_TO_QUESTION, questionId, answer.getAnswer());
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
            Answer answer = jdbcTemplate.queryForObject(SELECT_ANSWER_BY_ID, answerRowMapper, id);
            if (answer!=null) {
                jdbcTemplate.update(DELETE_ANSWER_BY_ID, id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Answer with given ID");
                return false;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean update(BigInteger id, Answer newAnswer) {
        if(isInvalidAnswer(newAnswer)){
            LOGGER.log(Level.WARNING, "Invalid Answer");
            return false;
        }
        try {
            Answer answer = jdbcTemplate.queryForObject(SELECT_ANSWER_BY_ID, answerRowMapper, id);
            if (answer!=null) {
                jdbcTemplate.update(UPDATE_ANSWER_BY_ID,
                        newAnswer.getAnswer(), id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Answer with given ID");
                return false;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    private boolean isInvalidAnswer(Answer answer) {
        if (answer == null)
            return true;
        else
            return answer.getAnswer() == null;
    }

}
