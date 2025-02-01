package com.msmk.linkedin.features.feed.service;

import java.util.List;

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
        post.setPicture(postDto.getPicture());
        return postRepository.save(post);
    }

    public Post editPost(Long postId, Long authorId, PostDto postDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        AuthenticationUser user = authenticationUserRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!post.getAuthor().equals(user)) {
            throw new IllegalArgumentException("User is not the author of the post");
        }
        post.setContent(postDto.getContent());
        post.setPicture(postDto.getPicture());
        return postRepository.save(post);
    }

    public List<Post> getFeedPosts(Long authenticatedUserId) {
        return postRepository.findByAuthorIdNotOrderByCreationDateDesc(authenticatedUserId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreationDateDesc();
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        AuthenticationUser user = authenticationUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!post.getAuthor().equals(user)) {
            throw new IllegalArgumentException("User is not the author of the post");
        }
        postRepository.delete(post);
        
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByAuthorId(userId);
    }

}
