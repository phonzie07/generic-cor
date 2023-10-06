package com.generic.core.base.controller.jwt.signin;

import com.generic.core.base.exceptions.RestExceptionError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserProfileController {

    @Autowired
    private UserProfileService service;

    @PostMapping("/user-profile/sign-in")
    public ResponseEntity<?> signIn(@RequestBody UserProfileEntity model)
        throws RestExceptionError {
        return service.signIn(model);
    }



}
