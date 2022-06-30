package com.dislinkt.postsapi.repository;

import com.dislinkt.postsapi.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {

}