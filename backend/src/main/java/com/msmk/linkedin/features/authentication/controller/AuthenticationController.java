package com.msmk.linkedin.features.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmk.linkedin.features.authentication.dto.AuthenticationRequestBody;
import com.msmk.linkedin.features.authentication.dto.AuthenticationResponseBody;
import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.authentication.service.AuthenticationService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }
    
    @GetMapping("/user")
    public AuthenticationUser getUser() {
        return authenticationService.getUser("sai@example.com");
    }

    @PostMapping("/login")
    public AuthenticationResponseBody loginPage(@Valid @RequestBody AuthenticationRequestBody loginRequestBody) {
        return authenticationService.login(loginRequestBody);
    }


    @PostMapping("/register")
    public AuthenticationResponseBody registerPage(@Valid @RequestBody AuthenticationRequestBody registerRequestBody) {
        return authenticationService.register(registerRequestBody);
    }

    
    
}
