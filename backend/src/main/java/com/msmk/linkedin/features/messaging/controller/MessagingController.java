package com.msmk.linkedin.features.messaging.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmk.linkedin.dto.Response;
import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.messaging.dto.MessageDto;
import com.msmk.linkedin.features.messaging.model.Conversation;
import com.msmk.linkedin.features.messaging.model.Message;
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

    @GetMapping("/conversations/{conversationId}")
    public Conversation getConversation(@RequestAttribute("authenticatedUser") AuthenticationUser user, @PathVariable Long conversationId) {
        return messagingService.getConversation(user, conversationId);
    }

    @PostMapping("/conversations")
    public Conversation createConversationAndAddMessage(@RequestAttribute("authenticatedUser") AuthenticationUser sender, @RequestBody MessageDto messageDto) {
        return messagingService.createConversationAndAddMessage(sender, messageDto.receiverId(), messageDto.content());
    }

    @PostMapping("/conversations/{conversationId}/messages")
    public Message addMessageToConversation(@RequestAttribute("authenticatedUser") AuthenticationUser sender, @RequestBody MessageDto messageDto, @PathVariable Long conversationId) {
        return messagingService.addMessageToConversation(conversationId, sender, messageDto.receiverId(),
                messageDto.content());
    }

    @PutMapping("/conversations/messages/{messageId}")
    public Response markMessageAsRead(@RequestAttribute("authenticatedUser") AuthenticationUser user, @PathVariable Long messageId) {
        messagingService.markMessageAsRead(user, messageId);
        return new Response("Message marked as read");
    }
    
}
