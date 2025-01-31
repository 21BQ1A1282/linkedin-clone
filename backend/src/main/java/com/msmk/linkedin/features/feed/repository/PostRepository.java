package com.msmk.linkedin.features.feed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msmk.linkedin.features.feed.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{

}
