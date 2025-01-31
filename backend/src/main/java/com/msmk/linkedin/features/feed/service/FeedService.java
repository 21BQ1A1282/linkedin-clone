package com.msmk.linkedin.features.feed.service;

import org.springframework.stereotype.Service;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.authentication.repository.AuthenticationUserRepository;
import com.msmk.linkedin.features.feed.dto.PostDto;
import com.msmk.linkedin.features.feed.model.Post;
import com.msmk.linkedin.features.feed.repository.PostRepository;

@Service
public class FeedService {

    private final PostRepository postRepository;
    private final AuthenticationUserRepository authenticationUserRepository;

    public FeedService(PostRepository postRepository, AuthenticationUserRepository authenticationUserRepository) {
        this.postRepository = postRepository;
        this.authenticationUserRepository = authenticationUserRepository;
    }

    public Post createPost(PostDto postDto, Long authorId) {
        AuthenticationUser author = authenticationUserRepository.findById(authorId).orElseThrow(()->new IllegalArgumentException("User not found"));
        Post post = new Post(postDto.getContent(), author);
        return postRepository.save(post);
    }

}
