package com.msmk.linkedin.features.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    @GetMapping("/user")
    public String getUser() {
        return "User";
    }   
}
