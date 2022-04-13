package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.controller.request.RequestUser;
import com.nightingale.simplelearning.dao.UserDAO;
import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.security.UserDetailsImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

public interface UserService extends UserDetailsService {

    public User getUserById(BigInteger id);

    public User getUserByUsername(String login);

    public boolean save(RequestUser user);

    public UserDetails loadUserByUsername(String username);

    public User getCurrentUser();
}