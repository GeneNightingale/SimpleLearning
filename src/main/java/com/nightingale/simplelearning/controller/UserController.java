package com.nightingale.simplelearning.controller;

import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{user_id}")
    public User getUserById(@PathVariable("user_id") BigInteger id) {
        return userService.getUserById(id);
    }

    @GetMapping("/current_user")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }
}
