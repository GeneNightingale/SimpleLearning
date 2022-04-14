package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.controller.request.RequestUser;
import com.nightingale.simplelearning.security.UserDetailsImpl;

public interface AuthorizationService {

    public UserDetailsImpl authenticate(RequestUser user);

    public String getAccessToken(UserDetailsImpl user);

    public void validateRequestUser(RequestUser user);

    public void validateRefreshToken(String token);

    public void validateResetPasswordToken(String token);
}
