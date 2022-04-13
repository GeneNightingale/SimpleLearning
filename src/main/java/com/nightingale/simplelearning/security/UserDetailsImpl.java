package com.nightingale.simplelearning.security;

import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.model.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl extends org.springframework.security.core.userdetails.User {
    private BigInteger id;
    private String name;
    private Role role;

    public UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static UserDetails fromUser(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        UserDetailsImpl userDetails = new UserDetailsImpl(user.getLogin(), user.getPassword(),
                Collections.singletonList(authority));

        userDetails.setId(BigInteger.valueOf(user.getUserId()));
        userDetails.setRole(user.getRole());
        userDetails.setName(user.getName());
        return userDetails;
    }

    public User toUser() {
        User user = new User();
        user.setName(this.getName());
        user.setUserId(this.getId().intValue());
        user.setLogin(this.getUsername());
        user.setRole(this.getRole());

        return user;
    }
}
