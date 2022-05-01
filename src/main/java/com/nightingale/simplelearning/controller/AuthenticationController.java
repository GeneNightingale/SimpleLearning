package com.nightingale.simplelearning.controller;

import com.nightingale.simplelearning.controller.request.RequestUser;
import com.nightingale.simplelearning.security.UserDetailsImpl;
import com.nightingale.simplelearning.service.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@Validated
@RestController
@RequestMapping(value = "/api/authenticate")
public class AuthenticationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthorizationService authorizationService;

    public AuthenticationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public ResponseEntity<?> signIn(@Valid @RequestBody RequestUser requestUser) {
        try {
            authorizationService.validateRequestUser(requestUser);

            UserDetailsImpl userDetails = authorizationService.authenticate(requestUser);

            String accessToken = authorizationService.getAccessToken(userDetails);

            return ResponseEntity.ok(new HashMap<String, Object>(){{
                put("accessToken", accessToken);
                put("user", userDetails.toUser());
            }});
        } catch (BadCredentialsException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalStateException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
