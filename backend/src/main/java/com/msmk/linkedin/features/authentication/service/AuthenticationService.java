package com.msmk.linkedin.features.authentication.service;

import org.springframework.stereotype.Service;

import com.msmk.linkedin.features.authentication.dto.AuthenticationRequestBody;
import com.msmk.linkedin.features.authentication.dto.AuthenticationResponseBody;
import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.authentication.repository.AuthenticationUserRepository;

@Service
public class AuthenticationService {

    private final AuthenticationUserRepository authenticationUserRepository;

    public AuthenticationService(AuthenticationUserRepository authenticationUserRepository){
        this.authenticationUserRepository = authenticationUserRepository;
    }

    public AuthenticationUser getUser(String email){
        return authenticationUserRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User note found"));
    }

    public AuthenticationResponseBody register(AuthenticationRequestBody registerRequestBody) {
        authenticationUserRepository.save(new AuthenticationUser(registerRequestBody.getEmail(),registerRequestBody.getPassword()));
        return new AuthenticationResponseBody("token","User registered successfully");
    }
}
