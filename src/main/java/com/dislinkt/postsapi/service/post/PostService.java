package com.dislinkt.postsapi.service.post;

import com.dislinkt.postsapi.domain.account.Account;
import com.dislinkt.postsapi.domain.post.Post;
import com.dislinkt.postsapi.exception.types.EntityNotFoundException;
import com.dislinkt.postsapi.repository.PostRepository;
import com.dislinkt.postsapi.service.account.AccountService;
import com.dislinkt.postsapi.web.rest.post.payload.PostDTO;
import com.dislinkt.postsapi.web.rest.post.payload.request.NewPostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountService accountService;

    public PostDTO findByUuid(String postUuid) {

        Post post = findOneByUuidOrElseThrowException(postUuid);

        PostDTO dto = new PostDTO();

        dto.setUuid(post.getUuid());
        dto.setText(post.getText());
        dto.setLikesCount(post.getLikesCount());
        dto.setDislikesCount(post.getDislikesCount());

        return dto;
    }

    public Page<PostDTO> findByAccount(String accountUuid, Pageable pageable) {

        Account account = accountService.findOneByUuidOrElseThrowException(accountUuid);

        Page<Post> posts = postRepository.findByAuthorId(account.getId(), pageable);

        return posts.map(entity -> {
            PostDTO dto = new PostDTO();

            dto.setUuid(entity.getUuid());
            dto.setText(entity.getText());
            dto.setLikesCount(entity.getLikesCount());
            dto.setDislikesCount(entity.getDislikesCount());

            return dto;
        });
    }

    public Post findOneByUuidOrElseThrowException(String uuid) {
        return postRepository.findOneByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    public PostDTO insertPost(NewPostRequest postRequest) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.findOneByUsernameOrElseThrowException(user.getUsername());

        Post post = new Post();

        post.setAuthor(account);
        post.setText(postRequest.getText());
        post.setLikesCount(0);
        post.setDislikesCount(0);

        postRepository.save(post);

        PostDTO postDTO = new PostDTO();
        postDTO.setText(post.getText());
        postDTO.setUuid(post.getUuid());
        postDTO.setLikesCount(post.getLikesCount());
        postDTO.setDislikesCount(post.getDislikesCount());

        return postDTO;
    }

    public PostDTO likePost(String postUuid) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.findOneByUsernameOrElseThrowException(user.getUsername());

        Post post = findOneByUuidOrElseThrowException(postUuid);

        post.addLike(account);

        postRepository.save(post);

        PostDTO postDTO = new PostDTO();
        postDTO.setText(post.getText());
        postDTO.setUuid(post.getUuid());
        postDTO.setLikesCount(post.getLikesCount());
        postDTO.setDislikesCount(post.getDislikesCount());

        return postDTO;
    }

    public PostDTO dislikePost(String postUuid) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.findOneByUsernameOrElseThrowException(user.getUsername());

        Post post = findOneByUuidOrElseThrowException(postUuid);

        post.addDislike(account);

        postRepository.save(post);

        PostDTO postDTO = new PostDTO();
        postDTO.setText(post.getText());
        postDTO.setUuid(post.getUuid());
        postDTO.setLikesCount(post.getLikesCount());
        postDTO.setDislikesCount(post.getDislikesCount());

        return postDTO;
    }
}
