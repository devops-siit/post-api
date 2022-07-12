package com.dislinkt.postsapi.web.rest.post;

import com.dislinkt.postsapi.service.post.PostService;
import com.dislinkt.postsapi.util.ReturnResponse;
import com.dislinkt.postsapi.web.rest.post.payload.PostDTO;
import com.dislinkt.postsapi.web.rest.post.payload.request.NewPostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostResource {

    @Autowired
    private PostService postService;

    @GetMapping("/{accountUuid}")
    public ResponseEntity<Page<PostDTO>> findByAccount(@PathVariable String accountUuid, Pageable pageable) {
        return ReturnResponse.entityGet(postService.findByAccount(accountUuid, pageable));
    }

    @PostMapping
    public ResponseEntity<PostDTO> insertPost(@RequestParam String loggedAccountUuid,
                                              @RequestBody NewPostRequest postRequest) {
        return ReturnResponse.entityCreated(postService.insertPost(loggedAccountUuid, postRequest));
    }

    @PutMapping("/{postUuid}/like")
    public ResponseEntity<PostDTO> likePost(@RequestParam String loggedAccountUuid,
                                            @PathVariable String postUuid) {
        return ReturnResponse.entityCreated(postService.likePost(postUuid, loggedAccountUuid));
    }

    @PutMapping("/{postUuid}/dislike")
    public ResponseEntity<PostDTO> dislikePost(@RequestParam String loggedAccountUuid,
                                               @PathVariable String postUuid) {
        return ReturnResponse.entityCreated(postService.dislikePost(postUuid, loggedAccountUuid));
    }
}
