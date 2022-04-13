package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.controller.request.RequestUser;
import com.nightingale.simplelearning.service.RegistrationService;
import com.nightingale.simplelearning.service.UserService;
import com.nightingale.simplelearning.util.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    //private final TokenService tokenService;

    public RegistrationServiceImpl(UserService userService,
                                   JwtTokenUtil jwtTokenUtil /*, TokenService tokenService*/) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        //this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public void register(RequestUser user) {
        System.out.println(user.getName() + " " + user.getLogin() + " " + user.getPassword() + " " + user.getRole());
        if (!userService.save(user)) {
            throw new IllegalStateException("Something went wrong when registering a new user");
        }
    }

    @Override
    public void isValidRequestUser(RequestUser user) {
        if (StringUtils.isBlank(user.getName())) {
            throw new IllegalStateException("Empty name");
        }

        if (StringUtils.isBlank(user.getLogin())) {
            throw new IllegalStateException("Empty username");
        }

        if (StringUtils.isBlank(user.getPassword())) {
            throw new IllegalStateException("Empty password");
        }

        if (StringUtils.isBlank(user.getRole().name())) {
            throw new IllegalStateException("Empty role somehow?");
        }
    }
}
