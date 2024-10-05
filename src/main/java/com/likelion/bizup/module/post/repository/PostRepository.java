package com.likelion.bizup.module.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.likelion.bizup.module.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
