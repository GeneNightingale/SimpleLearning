package com.nightingale.simplelearning.dao.impl;

import com.nightingale.simplelearning.dao.AppealDAO;
import com.nightingale.simplelearning.dao.CourseDAO;
import com.nightingale.simplelearning.dao.mapper.AppealRowMapper;
import com.nightingale.simplelearning.dao.mapper.CourseListRowMapper;
import com.nightingale.simplelearning.model.Appeal;
import com.nightingale.simplelearning.model.Course;
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
public class AppealDAOImpl implements AppealDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AppealRowMapper appealRowMapper;

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private CourseListRowMapper courseListRowMapper;

    private static final Logger LOGGER = Logger.getLogger(AppealDAOImpl.class.getName());

    @Override
    public Appeal getAppealById(BigInteger id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_APPEAL_BY_ID, appealRowMapper, id);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Appeal> getAllAppeals() {
        try {
            return jdbcTemplate.query(SELECT_ALL_APPEALS, appealRowMapper);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Appeal> getAllAppealsByTeacher(BigInteger id) {
        try {
            return jdbcTemplate.query(SELECT_ALL_APPEALS_BY_TEACHER, appealRowMapper, id);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Appeal> getAllAppealsByStudent(BigInteger id) {
        try {
            return jdbcTemplate.query(SELECT_ALL_APPEALS_BY_STUDENT, appealRowMapper, id);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Appeal> getAllAppealsByCourseId(BigInteger courseId) {
        try {
            return jdbcTemplate.query(SELECT_APPEALS_BY_COURSE_ID, appealRowMapper, courseId);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean approveAppealById(BigInteger id) {
        try {
            Appeal appeal = getAppealById(id);
            if (appeal!=null) {
                jdbcTemplate.update(APPROVE_APPEAL_BY_ID, id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No appeal with given ID");
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
    public boolean denyAppealById(BigInteger id) {
        try {
            Appeal appeal = getAppealById(id);
            if (appeal!=null) {
                jdbcTemplate.update(DENY_APPEAL_BY_ID, id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No appeal with given ID");
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
    public boolean deleteClosedAppealsByTeacher(BigInteger id) {
        jdbcTemplate.update(DELETE_CLOSED_APPEALS_BY_TEACHER, id);
        return true;
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean deleteAllAppealsByCourseId(BigInteger courseId) {
        try {
            Course course = courseDAO.getCourseById(courseId);
            if (course!=null) {
                jdbcTemplate.update(DELETE_All_APPEALS_BY_COURSE_ID, courseId);
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
    public boolean save(Appeal appeal, BigInteger courseId) {
        try {
            jdbcTemplate.update(INSERT_APPEAL, courseId, appeal.getUser().getUserId(), appeal.getText());
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
            Appeal appeal = getAppealById(id);
            if (appeal!=null) {
                jdbcTemplate.update(DELETE_APPEAL_BY_ID, id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Appeal with given ID");
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
    public boolean update(BigInteger id, Appeal newAppeal) {
        try {
            Appeal appeal = getAppealById(id);
            if (appeal!=null) {
                jdbcTemplate.update(UPDATE_APPEAL_BY_ID, appeal.getText(), appeal.getStatus(), appeal.getReasonForDenial(), id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Appeal with given ID");
                return false;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

}