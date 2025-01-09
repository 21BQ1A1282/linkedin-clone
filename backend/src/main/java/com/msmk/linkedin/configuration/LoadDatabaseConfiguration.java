package com.msmk.linkedin.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.authentication.repository.AuthenticationUserRepository;
import com.msmk.linkedin.features.authentication.utils.Encoder;

@Configuration
public class LoadDatabaseConfiguration {

    private final Encoder encoder;

    public LoadDatabaseConfiguration(Encoder encoder) {
        this.encoder = encoder;
    }

    @Bean
    public CommandLineRunner initDatabase(AuthenticationUserRepository authenticationUserRepository ){
        return args -> {
            AuthenticationUser authenticationUser = new AuthenticationUser("sai@example.com",encoder.encode("sai@82"));
            //authenticationUserRepository.save(authenticationUser);
        };
    }

}
