package com.dislinkt.postsapi.web.rest.comment;

import com.dislinkt.postsapi.service.comment.CommentService;
import com.dislinkt.postsapi.util.ReturnResponse;
import com.dislinkt.postsapi.web.rest.comment.payload.CommentDTO;
import com.dislinkt.postsapi.web.rest.comment.payload.request.NewCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentResource {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{postUuid}")
    public ResponseEntity<Page<CommentDTO>> findByPost(@PathVariable String postUuid, Pageable pageable) {
        return ReturnResponse.entityGet(commentService.findByPost(postUuid, pageable));
    }

    @PostMapping
    public ResponseEntity<CommentDTO> insertComment(@RequestBody NewCommentRequest commentRequest) {
        return ReturnResponse.entityGet(commentService.insertComment(commentRequest));
    }
}
