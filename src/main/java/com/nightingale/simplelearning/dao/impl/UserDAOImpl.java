package com.nightingale.simplelearning.dao.impl;

import com.nightingale.simplelearning.dao.UserDAO;
import com.nightingale.simplelearning.dao.mapper.UserMapper;
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
    private final UserMapper userMapper;

    public UserDAOImpl(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserByLogin(String login) {
        try {
            return jdbcTemplate.queryForObject(GET_USER_BY_LOGIN, userMapper, login);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public User getUserById(BigInteger id) {
        try {
            return jdbcTemplate.queryForObject(GET_USER_BY_ID, userMapper, id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean save(String name, String login, String password, Role role) {
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
        try {
            int result = jdbcTemplate.update(INSERT_USER, name, login, password, roleNum);
            return true;
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }
}
