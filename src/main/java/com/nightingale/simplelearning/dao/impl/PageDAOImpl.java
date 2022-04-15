package com.nightingale.simplelearning.dao.impl;

import com.nightingale.simplelearning.dao.PageDAO;
import com.nightingale.simplelearning.dao.mapper.PageRowMapper;
import com.nightingale.simplelearning.model.Page;
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
public class PageDAOImpl implements PageDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PageRowMapper pageRowMapper;

    private static final Logger LOGGER = Logger.getLogger(PageDAOImpl.class.getName());

    @Override
    public Page getPageById(BigInteger id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_PAGE_BY_ID, pageRowMapper, id);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Page> getAllPagesByLectureId(BigInteger lectureId) {
        try {
            return jdbcTemplate.query(SELECT_PAGES_BY_LECTURE_ID, pageRowMapper, lectureId);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Page> getAllPages() {
        try {
            return jdbcTemplate.query(SELECT_ALL_PAGES, pageRowMapper);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public boolean deleteAllPagesByLectureId(BigInteger id) {
        try {
            jdbcTemplate.update(DELETE_PAGES_BY_LECTURE_ID, id);
            return true;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = {DataAccessException.class})
    public boolean save(Page page, BigInteger lectureId) {
        if(isInvalidPage(page)){
            LOGGER.log(Level.WARNING, "Invalid Page");
            return false;
        }
        try {
            jdbcTemplate.update(INSERT_PAGE_TO_LECTURE, page.getPageNum(), page.getText(), lectureId);
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
            Page page = jdbcTemplate.queryForObject(SELECT_PAGE_BY_ID, pageRowMapper, id);
            if (page!=null) {
                jdbcTemplate.update(DELETE_PAGE_BY_ID, id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Page with given ID");
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
    public boolean update(BigInteger id, Page newPage) {
        if(isInvalidPage(newPage)){
            LOGGER.log(Level.WARNING, "Invalid Page");
            return false;
        }
        try {
            Page page = jdbcTemplate.queryForObject(SELECT_PAGE_BY_ID, pageRowMapper, id);
            if (page!=null) {
                jdbcTemplate.update(UPDATE_PAGE_BY_ID,
                        newPage.getPageNum(),
                        newPage.getText(), id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Page with given ID");
                return false;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    private boolean isInvalidPage(Page page) {
        if (page == null)
            return true;
        else
            return page.getPageNum() < 1 && page.getText() == null;
    }

}
