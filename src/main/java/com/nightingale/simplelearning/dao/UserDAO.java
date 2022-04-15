package com.nightingale.simplelearning.dao;

import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.model.enums.Role;

import java.math.BigInteger;

public interface UserDAO {

    String GET_USER_BY_LOGIN = "SELECT user.userId, user.name, user.login, user.password, role.roleName " +
            "from user " +
            "INNER JOIN role on user.roleId = role.roleId " +
            "where login = ?;";

    String GET_USER_BY_ID = "SELECT user.userId, user.name, user.login, user.password, role.roleName " +
            "from user " +
            "INNER JOIN role on user.roleId = role.roleId " +
            "where userId = ?;";

    String INSERT_USER = "INSERT INTO user (name, login, password, roleId) " +
            "VALUES (?, ?, ?, ?);";

    String GET_USER_BY_RESULT_ID = "SELECT * FROM user join result ON " +
            "user.userId = result.userId WHERE result.resultId = ?";

    public User getUserByLogin(String login);
    public User getUserById(BigInteger id);
    public User getUserByResultId(BigInteger id);
    boolean save(String name, String login, String password, Role role);
}
