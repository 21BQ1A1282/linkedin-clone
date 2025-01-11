package com.msmk.linkedin.features.authentication.service;

import org.springframework.stereotype.Service;

import com.msmk.linkedin.features.authentication.dto.AuthenticationRequestBody;
import com.msmk.linkedin.features.authentication.dto.AuthenticationResponseBody;
import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.authentication.repository.AuthenticationUserRepository;
import com.msmk.linkedin.features.authentication.utils.Encoder;
import com.msmk.linkedin.features.authentication.utils.JsonWebToken;

@Service
public class AuthenticationService {

    private final JsonWebToken jsonWebToken;

    private final Encoder encoder;

    private final AuthenticationUserRepository authenticationUserRepository;

    public AuthenticationService(JsonWebToken jsonWebToken,Encoder encoder,AuthenticationUserRepository authenticationUserRepository){
        this.jsonWebToken = jsonWebToken;
        this.encoder = encoder;
        this.authenticationUserRepository = authenticationUserRepository;
    }

    public AuthenticationUser getUser(String email){
        return authenticationUserRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User note found"));
    }

    public AuthenticationResponseBody register(AuthenticationRequestBody registerRequestBody) {
        authenticationUserRepository.save(new AuthenticationUser(registerRequestBody.getEmail(),encoder.encode(registerRequestBody.getPassword())));
        return new AuthenticationResponseBody("token","User registered successfully");
    }

    public AuthenticationResponseBody login(AuthenticationRequestBody loginRequestBody) {
        AuthenticationUser user = authenticationUserRepository.findByEmail(loginRequestBody.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (encoder.matches(loginRequestBody.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("password is incorrect");
        }
        String token = jsonWebToken.generateToken(loginRequestBody.getEmail());
        return new AuthenticationResponseBody(token, "Authentication Succeeded!!");
    }
}
