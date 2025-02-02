package com.msmk.linkedin.features.feed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msmk.linkedin.features.feed.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
