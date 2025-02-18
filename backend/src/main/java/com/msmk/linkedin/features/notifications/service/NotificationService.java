package com.msmk.linkedin.features.notifications.service;

import java.util.List;
import java.util.Set;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.feed.model.Comment;
import com.msmk.linkedin.features.messaging.model.Conversation;
import com.msmk.linkedin.features.messaging.model.Message;
import com.msmk.linkedin.features.networking.model.Connection;
import com.msmk.linkedin.features.notifications.model.Notification;
import com.msmk.linkedin.features.notifications.model.NotificationType;
import com.msmk.linkedin.features.notifications.repository.NotificationRepository;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public List<Notification> getUserNotifications(AuthenticationUser user) {
        return notificationRepository.findByRecipientOrderByCreationDateDesc(user);
    }

    public Notification markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        notification.setRead(true);
        messagingTemplate.convertAndSend("/topic/users/" + notification.getRecipient().getId() + "/notifications",notification);
        return notificationRepository.save(notification);
    }

    public void sendLikeNotification(AuthenticationUser author, AuthenticationUser recipient, Long resourceId) {
        if (author.getId().equals(recipient.getId())) {
            return;
        }

        Notification notification = new Notification(
                author,
                recipient,
                NotificationType.LIKE,
                resourceId);
        notificationRepository.save(notification);

        messagingTemplate.convertAndSend("/topic/users/" + recipient.getId() + "/notifications", notification);
    }

    public void sendLikeToPost(Long postId, Set<AuthenticationUser> likes) {
        messagingTemplate.convertAndSend("/topic/likes/" + postId, likes);
    }

    public void sendCommentNotification(AuthenticationUser author, AuthenticationUser recipient, Long resourceId) {
        if (author.getId().equals(recipient.getId())) {
            return;
        }

        Notification notification = new Notification(
                author,
                recipient,
                NotificationType.COMMENT,
                resourceId);
        notificationRepository.save(notification);

        messagingTemplate.convertAndSend("/topic/users/" + recipient.getId() + "/notifications", notification);
    }

    public void sendCommentToPost(Long postId, Comment comment) {
        messagingTemplate.convertAndSend("/topic/comments/" + postId, comment);
    }

    public void sendConversationToUsers(Long senderId, Long receiverId, Conversation conversation) {
        messagingTemplate.convertAndSend("/topic/users/" + senderId + "/conversations", conversation);
        messagingTemplate.convertAndSend("/topic/users/" + receiverId + "/conversations", conversation);
    }

    public void sendMessageToConversation(Long conversationId, Message message) {
        messagingTemplate.convertAndSend("/topic/conversations/" + conversationId + "/messages", message);
    }

    public void sendNewInvitationToUsers(Long senderId, Long receiverId, Connection connection) {
        messagingTemplate.convertAndSend("/topic/users/" + receiverId + "/connections/new", connection);
        messagingTemplate.convertAndSend("/topic/users/" + senderId + "/connections/new", connection);
    }


    public void sendInvitationAcceptedToUsers(Long senderId, Long receiverId, Connection connection) {
        messagingTemplate.convertAndSend("/topic/users/" + receiverId + "/connections/accepted", connection);
        messagingTemplate.convertAndSend("/topic/users/" + senderId + "/connections/accepted", connection);
    }

    public void sendRemoveConnectionToUsers(Long senderId, Long receiverId, Connection connection) {
        messagingTemplate.convertAndSend("/topic/users/" + receiverId + "/connections/remove", connection);
        messagingTemplate.convertAndSend("/topic/users/" + senderId + "/connections/remove", connection);
    }

    public void sendConnectionSeenNotification(Long id, Connection connection) {
        messagingTemplate.convertAndSend("/topic/users/" + id + "/connections/seen", connection);
    }

}
