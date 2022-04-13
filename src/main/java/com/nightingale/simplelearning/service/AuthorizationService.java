package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.controller.request.RequestUser;
import com.nightingale.simplelearning.security.UserDetailsImpl;
import com.nightingale.simplelearning.util.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    //private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthorizationService(UserService userService, JwtTokenUtil jwtTokenUtil, //TokenService tokenService,
                                    AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        //this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public UserDetailsImpl authenticate(RequestUser user) throws BadCredentialsException {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        System.out.println(userDetails.getId() + " " + userDetails.getName() + " " + userDetails.getRole());

        return userDetails;
    }

    public String getAccessToken(UserDetailsImpl user) {
        return jwtTokenUtil.generateAccessToken(user);
    }

    /*public String getAccessTokenByRefreshToken(String refreshToken) {
        if (!tokenService.existRefreshToken(refreshToken)) {
            throw new IllegalStateException("Refresh token does not exist");
        }

        return jwtTokenUtil.generateAccessTokenByRefreshToken(refreshToken);
    }*/

    /*@Transactional
    public String getRefreshToken(UserDetailsImpl user) {
        if (tokenService.existRefreshToken(user.getId())) {
            tokenService.deleteRefreshToken(user.getId());
        }

        String refreshToken = jwtTokenUtil.generateRefreshToken(user);
        tokenService.saveRefreshToken(user.getId(), refreshToken);
        return refreshToken;
    }*/

    public void validateRequestUser(RequestUser user) throws IllegalStateException {
        /*if (StringUtils.isBlank(user.getName())) {
            throw new IllegalStateException("Name is empty.");
        }*/

        if (StringUtils.isBlank(user.getLogin())) {
            throw new IllegalStateException("Username is empty.");
        }

        if (StringUtils.isBlank(user.getPassword())) {
            throw new IllegalStateException("Password is empty");
        }
    }

    public void validateRefreshToken(String token) throws IllegalStateException {
        if (!jwtTokenUtil.validate(token)) {
            throw new IllegalStateException("Invalid refresh token");
        }
    }

    public void validateResetPasswordToken(String token) throws IllegalStateException {
        if (!jwtTokenUtil.validate(token)) {
            throw new IllegalStateException("Invalid reset password token");
        }
    }
}

