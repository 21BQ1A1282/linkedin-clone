package com.msmk.linkedin.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.authentication.repository.AuthenticationUserRepository;

@Configuration
public class LoadDatabaseConfiguration {

    @Bean
    public CommandLineRunner initDatabase(AuthenticationUserRepository authenticationUserRepository ){
        return args -> {
            AuthenticationUser authenticationUser = new AuthenticationUser(1L,"sai@example.com","sai82");
            authenticationUserRepository.save(authenticationUser);
        };
    }

}
