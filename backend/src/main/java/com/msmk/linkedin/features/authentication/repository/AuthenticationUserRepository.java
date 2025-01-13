package com.msmk.linkedin.features.authentication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;


public interface AuthenticationUserRepository extends JpaRepository<AuthenticationUser,Long>{

    Optional<AuthenticationUser> findByEmail(String email);

    List<AuthenticationUser> findAllByIdNot(Long id);
}
