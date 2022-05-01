package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.controller.request.RequestUser;
import com.nightingale.simplelearning.security.UserDetailsImpl;
import com.nightingale.simplelearning.service.AuthorizationService;
import com.nightingale.simplelearning.service.UserService;
import com.nightingale.simplelearning.util.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public AuthorizationServiceImpl(UserService userService, JwtTokenUtil jwtTokenUtil,
                                    AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetailsImpl authenticate(RequestUser user) throws BadCredentialsException {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userDetails;
    }

    @Override
    public String getAccessToken(UserDetailsImpl user) {
        return jwtTokenUtil.generateAccessToken(user);
    }

    @Override
    public void validateRequestUser(RequestUser user) throws IllegalStateException {
        if (StringUtils.isBlank(user.getLogin())) {
            throw new IllegalStateException("Username is empty.");
        }

        if (StringUtils.isBlank(user.getPassword())) {
            throw new IllegalStateException("Password is empty");
        }
    }

    @Override
    public void validateRefreshToken(String token) throws IllegalStateException {
        if (!jwtTokenUtil.validate(token)) {
            throw new IllegalStateException("Invalid refresh token");
        }
    }

    @Override
    public void validateResetPasswordToken(String token) throws IllegalStateException {
        if (!jwtTokenUtil.validate(token)) {
            throw new IllegalStateException("Invalid reset password token");
        }
    }
}

