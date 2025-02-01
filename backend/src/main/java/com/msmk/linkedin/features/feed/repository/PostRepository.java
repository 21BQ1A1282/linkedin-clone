package com.msmk.linkedin.features.feed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msmk.linkedin.features.feed.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{

    List<Post> findByAuthorIdNotOrderByCreationDateDesc(Long authenticatedUserId);

    List<Post> findAllByOrderByCreationDateDesc();

    List<Post> findByAuthorId(Long userId);

}
