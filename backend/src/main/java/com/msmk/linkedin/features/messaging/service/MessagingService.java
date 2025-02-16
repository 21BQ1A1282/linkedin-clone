package com.msmk.linkedin.features.messaging.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.messaging.model.Conversation;
import com.msmk.linkedin.features.messaging.repository.ConversationRepository;

@Service
public class MessagingService {

    private final ConversationRepository conversationRepository;

    public MessagingService(ConversationRepository conversationRepository){
        this.conversationRepository = conversationRepository;
    }

    public List<Conversation> getConversationsOfUser(AuthenticationUser user) {
        return conversationRepository.findByAuthorOrRecipient(user, user);
    }

}
