package com.dislinkt.postsapi.service;
import static com.dislinkt.postsapi.constants.PostConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dislinkt.postsapi.domain.post.Post;
import com.dislinkt.postsapi.service.post.PostService;
import com.dislinkt.postsapi.web.rest.post.payload.PostDTO;
import com.dislinkt.postsapi.web.rest.post.payload.request.NewPostRequest;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class PostServiceIntegrationTest {

	@Autowired
	private PostService service;
	
	@Test
	public void testFindByAccount() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<PostDTO> found = service.findByAccount(DB_POST_ACCOUNT_UUID_1, pageable);
		
		assertEquals(1, found.getNumberOfElements());
	}
	
	@Test
	public void testFindOneByUuidOrElseThrowException() {
		Post found = service.findOneByUuidOrElseThrowException(DB_POST_UUID_1);
		assertEquals(DB_POST_UUID_1, found.getUuid());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertPost() {
		NewPostRequest req = new NewPostRequest();
		req.setText(NEW_POST_TEXT);
		PostDTO created = service.insertPost(DB_POST_ACCOUNT_UUID_1, req);
		
		assertEquals(NEW_POST_TEXT, created.getText());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testLikePost() {
		PostDTO likedPost = service.likePost(DB_POST_UUID_1, DB_ACCOUNT_UUID_3);
		
		assertEquals(2, likedPost.getLikesCount());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDislikePost() {
		PostDTO likedPost = service.dislikePost(DB_POST_UUID_1, DB_ACCOUNT_UUID_3);
		
		assertEquals(2, likedPost.getDislikesCount());
	}
}
