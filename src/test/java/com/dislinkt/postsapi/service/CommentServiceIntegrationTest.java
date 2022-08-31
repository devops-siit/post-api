package com.dislinkt.postsapi.service;

import static com.dislinkt.postsapi.constants.CommentConstants.*;
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

import com.dislinkt.postsapi.service.comment.CommentService;
import com.dislinkt.postsapi.web.rest.comment.payload.CommentDTO;
import com.dislinkt.postsapi.web.rest.comment.payload.request.NewCommentRequest;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class CommentServiceIntegrationTest {

	@Autowired
	private CommentService service;
	
	@Test
	@Transactional
	public void testFindByPost() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CommentDTO> found = service.findByPost(DB_COMMENT_POST_UUID_1, pageable);
		
		assertEquals(1, found.getNumberOfElements());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertComment() {
		NewCommentRequest req = new NewCommentRequest();
		req.setPostUuid(DB_COMMENT_POST_UUID_1);
		req.setText(NEW_COMMENT_TEXT);
		
		CommentDTO comment = service.insertComment(DB_COMMENT_AUTHOR_UUID_1, req);
		assertEquals(NEW_COMMENT_TEXT, comment.getText());
	}
}
