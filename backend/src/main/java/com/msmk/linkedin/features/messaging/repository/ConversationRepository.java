package com.msmk.linkedin.features.messaging.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.messaging.model.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByAuthorAndRecipient(AuthenticationUser author, AuthenticationUser recipient);

    List<Conversation> findByAuthorOrRecipient(AuthenticationUser userOne, AuthenticationUser userTwo);
}
