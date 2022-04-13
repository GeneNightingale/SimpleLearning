package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.controller.request.RequestUser;
import com.nightingale.simplelearning.dao.UserDAO;
import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.security.UserDetailsImpl;
import com.nightingale.simplelearning.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;

    public UserServiceImpl(@Lazy PasswordEncoder passwordEncoder, UserDAO userDAO) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
    }

    @Override
    public User getUserById(BigInteger id) {
        return userDAO.getUserById(id);
    }

    @Override
    public User getUserByUsername(String login) {
        return userDAO.getUserByLogin(login);
    }

    @Override
    public boolean save(RequestUser user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userDAO.save(user.getName(), user.getLogin(), encodedPassword, user.getRole());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found by username: " + username);
        }

        return UserDetailsImpl.fromUser(user);
    }

    @Override
    public User getCurrentUser() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUserById(user.getId());
    }
}