package com.nightingale.simplelearning.model;
import com.nightingale.simplelearning.model.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

public class User {

    @Positive
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String name;

    private String login;

    private String password;

    private Role role;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUserId(), user.getUserId()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                getRole() == user.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getName(), getLogin(), getPassword(), getRole());
    }
}
