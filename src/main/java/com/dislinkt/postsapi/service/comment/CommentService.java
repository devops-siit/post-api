package com.dislinkt.postsapi.service.comment;

import com.dislinkt.postsapi.domain.account.Account;
import com.dislinkt.postsapi.domain.comment.Comment;
import com.dislinkt.postsapi.domain.post.Post;
import com.dislinkt.postsapi.repository.CommentRepository;
import com.dislinkt.postsapi.service.account.AccountService;
import com.dislinkt.postsapi.service.account.payload.AccountDTO;
import com.dislinkt.postsapi.service.post.PostService;
import com.dislinkt.postsapi.web.rest.comment.payload.CommentDTO;
import com.dislinkt.postsapi.web.rest.comment.payload.request.NewCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    public Page<CommentDTO> findByPost(String postUuid, Pageable pageable) {

        Post post = postService.findOneByUuidOrElseThrowException(postUuid);

        Page<Comment> comments = commentRepository.findByPostId(post.getId(), pageable);

        return comments.map(comment -> {
            CommentDTO dto = new CommentDTO();

            dto.setText(comment.getText());
            dto.setUuid(comment.getUuid());

            AccountDTO author = new AccountDTO();
            author.setUuid(comment.getAuthor().getUuid());
            author.setName(comment.getAuthor().getName());
            author.setUsername(comment.getAuthor().getUsername());

            dto.setAuthor(author);

            return dto;
        });
    }

    public CommentDTO insertComment(String loggedAccountUuid, NewCommentRequest commentRequest) {

        Post post = postService.findOneByUuidOrElseThrowException(commentRequest.getPostUuid());

        Account account = accountService.findOneByUuidOrElseThrowException(loggedAccountUuid);

        Comment comment = new Comment();

        comment.setText(commentRequest.getText());
        comment.setPost(post);
        comment.setAuthor(account);

        commentRepository.save(comment);

        CommentDTO dto = new CommentDTO();

        dto.setText(comment.getText());
        dto.setUuid(comment.getUuid());

        AccountDTO author = new AccountDTO();
        author.setUuid(comment.getAuthor().getUuid());
        author.setName(comment.getAuthor().getName());
        author.setUsername(comment.getAuthor().getUsername());

        dto.setAuthor(author);

        return dto;
    }
}
