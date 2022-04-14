package com.nightingale.simplelearning.dao.mapper;

import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.model.enums.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setUserId(rs.getInt("userId"));
        user.setName(rs.getString("name"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        switch (rs.getString("roleName")) {
            case "ADMIN":
                user.setRole(Role.ADMIN);
                break;
            case "STUDENT":
                user.setRole(Role.STUDENT);
                break;
            case "TEACHER":
                user.setRole(Role.TEACHER);
                break;
        }
        return user;
    }
}
