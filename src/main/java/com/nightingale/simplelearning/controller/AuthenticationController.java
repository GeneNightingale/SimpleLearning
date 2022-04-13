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
    }

    /*@PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();
            authorizationService.validateRefreshToken(refreshToken);

            String accessToken = authorizationService.getAccessTokenByRefreshToken(refreshToken);
            return ResponseEntity.ok(new HashMap<String, String>(){{
                put("accessToken", accessToken);
                put("refreshToken", refreshToken);
            }});
        } catch (IllegalStateException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/
}
