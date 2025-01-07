package com.msmk.linkedin.features.authentication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "users")
public class AuthenticationUser {

    @Id
    private Long id;
    private String email;
    private String password;

    public AuthenticationUser(){
    }
    
    public AuthenticationUser(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    
}
