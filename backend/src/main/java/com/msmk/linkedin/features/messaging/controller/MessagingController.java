package com.msmk.linkedin.features.messaging.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.messaging.model.Conversation;
import com.msmk.linkedin.features.messaging.service.MessagingService;


@RestController
@RequestMapping("/api/v1/messaging")
public class MessagingController {

    private final MessagingService messagingService;

    public MessagingController(MessagingService messagingService) {
        this.messagingService = messagingService;
    }


    @GetMapping("/conversations")
    public List<Conversation> getConversations(@RequestAttribute("authenticatedUser") AuthenticationUser user) {
        return messagingService.getConversationsOfUser(user);
    }
    
}
