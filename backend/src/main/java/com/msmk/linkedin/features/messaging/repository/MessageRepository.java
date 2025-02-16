package com.msmk.linkedin.features.messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msmk.linkedin.features.messaging.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
