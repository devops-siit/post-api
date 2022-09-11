package com.dislinkt.postsapi.service;
import static com.dislinkt.postsapi.constants.PostConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.dislinkt.postsapi.domain.post.Post;
import com.dislinkt.postsapi.service.post.PostService;
import com.dislinkt.postsapi.web.rest.post.payload.PostDTO;
import com.dislinkt.postsapi.web.rest.post.payload.request.NewPostRequest;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestExecutionListeners(
	    listeners = {TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class},
	    inheritListeners = false
	    
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostServiceIntegrationTest {

	@Autowired
	private PostService service;
	
	private void logUser1() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	
		Set<? extends GrantedAuthority> auth = new HashSet<>(); 
		UserDetails principal = new User(DB_POST_ACCOUNT_USERNAME_1, "aPassword", auth);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);	
	}
	
	private void logUser3() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	
		Set<? extends GrantedAuthority> auth = new HashSet<>(); 
		UserDetails principal = new User(DB_ACCOUNT_USERNAME_3, "aPassword", auth);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);	
	}
	
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
		logUser1();
		NewPostRequest req = new NewPostRequest();
		req.setText(NEW_POST_TEXT);
		PostDTO created = service.insertPost(req);
		
		assertEquals(NEW_POST_TEXT, created.getText());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testLikePost() {
		logUser3();
		PostDTO likedPost = service.likePost(DB_POST_UUID_1);
		
		assertEquals(2, likedPost.getLikesCount());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDislikePost() {
		logUser3();
		PostDTO likedPost = service.dislikePost(DB_POST_UUID_1);
		
		assertEquals(2, likedPost.getDislikesCount());
	}
}
