package com.msmk.linkedin.features.messaging.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.authentication.service.AuthenticationService;
import com.msmk.linkedin.features.messaging.model.Conversation;
import com.msmk.linkedin.features.messaging.model.Message;
import com.msmk.linkedin.features.messaging.repository.ConversationRepository;
import com.msmk.linkedin.features.messaging.repository.MessageRepository;
import com.msmk.linkedin.features.notifications.service.NotificationService;

import jakarta.transaction.Transactional;

@Service
public class MessagingService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final AuthenticationService authenticationService;
    private final NotificationService notificationService;

    public MessagingService(ConversationRepository conversationRepository,MessageRepository messageRepository, AuthenticationService authenticationService, NotificationService notificationService){
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.authenticationService = authenticationService;
        this.notificationService = notificationService;
    }

    public List<Conversation> getConversationsOfUser(AuthenticationUser user) {
        return conversationRepository.findByAuthorOrRecipient(user, user);
    }

    public Conversation getConversation(AuthenticationUser user, Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new IllegalArgumentException("Conversation not found"));
        if (!conversation.getAuthor().getId().equals(user.getId())&& !conversation.getRecipient().getId().equals(user.getId())) {
            throw new IllegalArgumentException("User not authorized to view conversation");
        }
        return conversation;
    }

    @Transactional
    public Conversation createConversationAndAddMessage(AuthenticationUser sender, Long receiverId, String content) {
        AuthenticationUser receiver = authenticationService.getUserById(receiverId);
        conversationRepository.findByAuthorAndRecipient(sender, receiver).ifPresentOrElse(
                conversation -> {
                    throw new IllegalArgumentException(
                            "Conversation already exists, use the conversation id to send messages.");
                },
                () -> {
                });

        conversationRepository.findByAuthorAndRecipient(receiver, sender).ifPresentOrElse(
                conversation -> {
                    throw new IllegalArgumentException(
                            "Conversation already exists, use the conversation id to send messages.");
                },
                () -> {
                });

        Conversation conversation = conversationRepository.save(new Conversation(sender, receiver));
        Message message = new Message(sender, receiver, conversation, content);
        messageRepository.save(message);
        conversation.getMessages().add(message);
        notificationService.sendConversationToUsers(sender.getId(), receiver.getId(), conversation);
        return conversation;
    }

    public Message addMessageToConversation(Long conversationId, AuthenticationUser sender, Long receiverId, String content) {
        AuthenticationUser receiver = authenticationService.getUserById(receiverId);
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found"));

        if (!conversation.getAuthor().getId().equals(sender.getId())
                && !conversation.getRecipient().getId().equals(sender.getId())) {
            throw new IllegalArgumentException("User not authorized to send message to this conversation");
        }

        if (!conversation.getAuthor().getId().equals(receiver.getId())
                && !conversation.getRecipient().getId().equals(receiver.getId())) {
            throw new IllegalArgumentException("Receiver is not part of this conversation");
        }

        Message message = new Message(sender, receiver, conversation, content);
        messageRepository.save(message);
        conversation.getMessages().add(message);
        notificationService.sendMessageToConversation(conversation.getId(), message);
        notificationService.sendConversationToUsers(sender.getId(), receiver.getId(), conversation);
        return message;
    }

    public void markMessageAsRead(AuthenticationUser user, Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        if (!message.getReceiver().getId().equals(user.getId())) {
            throw new IllegalArgumentException("User not authorized to mark message as read");
        }

        
        message.setIsRead(true);
        messageRepository.save(message);
    }


}
