package com.msmk.linkedin.features.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;

public interface AuthenticationUserRepository extends JpaRepository<AuthenticationUser,Long>{
}
