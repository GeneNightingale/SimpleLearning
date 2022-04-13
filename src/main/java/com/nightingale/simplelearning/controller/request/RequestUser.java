package com.nightingale.simplelearning.controller.request;

import com.nightingale.simplelearning.model.enums.Role;

import javax.validation.constraints.Size;

public class RequestUser {
    //@Size(min = 4, max = 50)
    protected String name;

    protected String login;

    protected String password;

    protected Role role;

    public RequestUser(String name, String login, String password, Role role) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
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
}
