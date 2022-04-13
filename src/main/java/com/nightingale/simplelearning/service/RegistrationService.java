package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.controller.request.RequestUser;

public interface RegistrationService {
    void register(RequestUser user);
    void isValidRequestUser(RequestUser user);
}
