package com.nightingale.simplelearning.dao.impl;

import com.nightingale.simplelearning.dao.UserDAO;
import com.nightingale.simplelearning.dao.mapper.UserRowMapper;
import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.model.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigInteger;

@Repository
public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public UserDAOImpl(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.userRowMapper = userRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserByLogin(String login) {
        try {
            return jdbcTemplate.queryForObject(GET_USER_BY_LOGIN, userRowMapper, login);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public User getUserById(BigInteger id) {
        try {
            return jdbcTemplate.queryForObject(GET_USER_BY_ID, userRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public User getUserByResultId(BigInteger id) {
        try {
            return jdbcTemplate.queryForObject(GET_USER_BY_RESULT_ID, userRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean save(String name, String login, String password, Role role) {
        try {
            int result = jdbcTemplate.update(INSERT_USER, name, login, password, getNumFromRole(role));
            return true;
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    private int getNumFromRole(Role role){
        int roleNum;
        switch (role.name()) {
            case "ADMIN":
                roleNum = 1;
                break;
            case "STUDENT":
                roleNum = 3;
                break;
            case "TEACHER":
                roleNum = 2;
                break;
            default:
                roleNum = 3;
        }
        return roleNum;
    }
}
