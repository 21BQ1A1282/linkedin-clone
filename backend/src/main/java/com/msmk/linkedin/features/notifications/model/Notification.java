package com.msmk.linkedin.features.notifications.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AuthenticationUser recipient;

    @ManyToOne
    private AuthenticationUser actor;
    
    private boolean isRead;
    private NotificationType type;
    private Long resourceId;

    @CreationTimestamp
    private LocalDateTime creationDate;

    public Notification(AuthenticationUser actor, AuthenticationUser recipient, NotificationType type, Long resourceId) {
        this.actor = actor;
        this.recipient = recipient;
        this.type = type;
        this.isRead = false;
        this.resourceId = resourceId;
    }

    public Notification() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public AuthenticationUser getRecipient() {
        return recipient;
    }

    public void setRecipient(AuthenticationUser recipient) {
        this.recipient = recipient;
    }

    public AuthenticationUser getActor() {
        return actor;
    }

    public void setActor(AuthenticationUser actor) {
        this.actor = actor;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
