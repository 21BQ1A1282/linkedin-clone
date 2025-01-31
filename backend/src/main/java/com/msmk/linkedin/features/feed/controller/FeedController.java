package com.msmk.linkedin.features.feed.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.feed.dto.PostDto;
import com.msmk.linkedin.features.feed.model.Post;
import com.msmk.linkedin.features.feed.service.FeedService;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService){
        this.feedService = feedService;
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto,
            @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        Post post = feedService.createPost(postDto, user.getId());
        return ResponseEntity.ok(post);
    }
}
