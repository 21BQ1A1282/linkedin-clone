package com.msmk.linkedin.features.networking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.networking.model.Connection;
import com.msmk.linkedin.features.networking.model.Status;
import com.msmk.linkedin.features.networking.service.ConnectionService;

@RestController
@RequestMapping("/api/v1/networking")
public class ConnectionController {
    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @GetMapping("/connections")
    public List<Connection> getUserConnections(@RequestAttribute("authenticatedUser") AuthenticationUser user, @RequestParam(required = false) Status status, @RequestParam(required = false) Long userId) {
        if(userId!=null){
            return connectionService.getUserConnections(userId,status);
        }
        return connectionService.getUserConnections(user, status);
    }

    @PostMapping("/connections")
    public Connection sendConnectionRequest(@RequestAttribute("authenticatedUser") AuthenticationUser sender, @RequestParam Long recipientId) {
        return connectionService.sendConnectionRequest(sender, recipientId);
    }

    @PutMapping("/connections/{id}")
    public Connection acceptConnectionRequest(@RequestAttribute("authenticatedUser") AuthenticationUser recipient, @PathVariable Long id) {
        return connectionService.acceptConnectionRequest(recipient, id);
    }

    @DeleteMapping("/connections/{id}")
    public Connection rejectOrCancelConnection(@RequestAttribute("authenticatedUser") AuthenticationUser recipient, @PathVariable Long id) {
        return connectionService.rejectOrCancelConnection(recipient, id);
    }

    @PutMapping("/connections/{id}/seen")
    public Connection markConnectionAsSeen(@RequestAttribute("authenticatedUser") AuthenticationUser user, @PathVariable Long id) {
        return connectionService.markConnectionAsSeen(user, id);
    }

    @GetMapping("/suggestions")
    public List<AuthenticationUser> getConnectionSuggestions(@RequestAttribute("authenticatedUser") AuthenticationUser user, @RequestParam(required = false, defaultValue = "6") Integer limit) {
        return connectionService.getRecommendations(user.getId(), limit);
    }
}
