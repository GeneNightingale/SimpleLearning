package com.nightingale.simplelearning.controller;

import com.nightingale.simplelearning.controller.request.RequestUser;
import com.nightingale.simplelearning.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@Validated
@RestController
@RequestMapping(value = "/api/course")
public class CourseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
    //private final CourseService courseService;

    /*public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }*/

    //CHANGE ALL OF THIS TO COURSES!!!!!!!
    /*@PostMapping
    public ResponseEntity<?> signIn(@Valid @RequestBody RequestUser requestUser) {
        System.out.println("Login: " + requestUser.getLogin() + ", Pass: " + requestUser.getPassword());
        try {
            authorizationService.validateRequestUser(requestUser);

            UserDetailsImpl userDetails = authorizationService.authenticate(requestUser);

            String accessToken = authorizationService.getAccessToken(userDetails);
            //String refreshToken = authorizationService.getRefreshToken(userDetails);

            return ResponseEntity.ok(new HashMap<String, Object>(){{
                put("accessToken", accessToken);
                //put("refreshToken", refreshToken);
                put("user", userDetails.toUser());
            }});
        } catch (BadCredentialsException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalStateException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/
}

