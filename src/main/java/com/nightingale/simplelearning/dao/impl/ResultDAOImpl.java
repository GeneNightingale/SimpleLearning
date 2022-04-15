package com.nightingale.simplelearning.dao.impl;

import com.nightingale.simplelearning.dao.*;
import com.nightingale.simplelearning.dao.mapper.ResultRowMapper;
import com.nightingale.simplelearning.dao.mapper.TestRowMapper;
import com.nightingale.simplelearning.dao.mapper.UserRowMapper;
import com.nightingale.simplelearning.model.*;
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
public class ResultDAOImpl implements ResultDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResultRowMapper resultRowMapper;

    @Autowired
    private TestRowMapper testRowMapper;

    @Autowired
    private UserRowMapper userRowMapper;

    private static final Logger LOGGER = Logger.getLogger(ResultDAOImpl.class.getName());

    @Override
    public Result getResultById(BigInteger id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_RESULT_BY_ID, resultRowMapper, id);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Result> getAllResultsByTestId(BigInteger testId) {
        try {
            return jdbcTemplate.query(SELECT_RESULTS_BY_TEST_ID, resultRowMapper, testId);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Result> getAllResultsByStudentId(BigInteger studentId) {
        try {
            return jdbcTemplate.query(SELECT_RESULTS_BY_STUDENT_ID, resultRowMapper, studentId);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Result> getAllResultsByStudentAndTestId(BigInteger studentId, BigInteger testId) {
        try {
            return jdbcTemplate.query(SELECT_RESULTS_BY_STUDENT_AND_TEST_ID, resultRowMapper, studentId, testId);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Result> getAllResults() {
        try {
            return jdbcTemplate.query(SELECT_ALL_RESULTS, resultRowMapper);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean deleteAllResultsByTestId(BigInteger id) {
        try {
            jdbcTemplate.update(DELETE_RESULTS_BY_TEST_ID, id);
            return true;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    /*@Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean deleteAllResultsByStudentId(BigInteger id) {
        try {
            //Check if this student exists at all
            User student = userDAO.getUserById(id);
            //If they exist, delete their results
            if (student!=null) {
                jdbcTemplate.update(DELETE_RESULTS_BY_USER_ID, id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No User with given ID");
                return false;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }*/

    @Override
    @Transactional(rollbackFor = {DataAccessException.class})
    public boolean save(Result result) {
        try {
            BigInteger studentId = BigInteger.valueOf(result.getStudentId());
            BigInteger testId = BigInteger.valueOf(result.getTestId());
            if(isInvalidResult(result, studentId, testId)){
                LOGGER.log(Level.WARNING, "Incorrect Student or Test ID");
                return false;
            } else {
                jdbcTemplate.update(INSERT_RESULT, result.getScore(), testId, studentId);
                return true;
            }
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
            jdbcTemplate.update(DELETE_RESULT_BY_ID, id);
            return true;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean update(BigInteger id, Result newResult) {
        try {
            BigInteger studentId = BigInteger.valueOf(newResult.getStudentId());
            BigInteger testId = BigInteger.valueOf(newResult.getTestId());
            if(isInvalidResult(newResult, studentId, testId)){
                LOGGER.log(Level.WARNING, "Incorrect Student or Test ID");
                return false;
            } else {
                jdbcTemplate.update(UPDATE_RESULT_BY_ID, newResult.getScore(), testId, studentId, id);
                return true;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    private boolean isInvalidResult(Result result, BigInteger studentId, BigInteger testId) {
        if (result == null)
            return true;
        else {
            User student = jdbcTemplate.queryForObject(GET_USER_BY_ID, userRowMapper, studentId);
            Test test = jdbcTemplate.queryForObject(SELECT_TEST_BY_ID, testRowMapper, testId);//testDAO.getTestById(testId);
            return student == null || test == null;
        }
    }

}


