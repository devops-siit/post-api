package com.dislinkt.postsapi.repository;

import com.dislinkt.postsapi.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByAuthorId(Long accountId, Pageable pageable);

    Optional<Post> findOneByUuid(String uuid);
}