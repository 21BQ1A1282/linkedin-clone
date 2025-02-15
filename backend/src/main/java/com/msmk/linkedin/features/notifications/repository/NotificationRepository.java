package com.msmk.linkedin.features.notifications.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.notifications.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>  {

    List<Notification> findByRecipientOrderByCreationDateDesc(AuthenticationUser user);

}
