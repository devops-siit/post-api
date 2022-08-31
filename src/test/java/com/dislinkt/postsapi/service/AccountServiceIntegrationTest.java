package com.dislinkt.postsapi.service;

import static com.dislinkt.postsapi.constants.AccountConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dislinkt.postsapi.domain.account.Account;
import com.dislinkt.postsapi.event.AccountCreatedEvent;
import com.dislinkt.postsapi.service.account.AccountService;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class AccountServiceIntegrationTest {
	
	@Autowired
	private AccountService service;
	
	@Test
	public void testFindByUuid() throws Exception {
		
		Account foundAccount = service.findOneByUuidOrElseThrowException(DB_ACCOUNT_UUID_2);
		
		assertEquals(DB_ACCOUNT_ID_2, foundAccount.getId()); 
    }
	
	@Test
	public void testFindDTOByUuidException() throws Exception {
		
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	        		Account foundAccount = service.findOneByUuidOrElseThrowException(DB_ACCOUNT_UUID_DoesntExist);
	            }
	    );
	    assertEquals("Account not found", exception.getMessage());
    
    }

	@Test
	@Transactional
	@Rollback(true)
	public void testInsertAccount() throws Exception {
		AccountCreatedEvent event = new AccountCreatedEvent();
		event.setName(NEW_ACCOUNT_NAME);
		
		event.setUsername(NEW_ACCOUNT_USERNAME);
		event.setUuid(NEW_ACCOUNT_UUID);
		service.insertAccount(event);
		
		Account acc = service.findOneByUsernameOrElseThrowException(NEW_ACCOUNT_USERNAME);
		assertEquals(NEW_ACCOUNT_USERNAME, acc.getUsername());

    }
	

	@Test
	@Transactional
	public void testInsertAccountUsernameExists() throws Exception {
		
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	AccountCreatedEvent event = new AccountCreatedEvent();
	        		event.setName(NEW_ACCOUNT_NAME);
	        		
	        		event.setUsername(DB_ACCOUNT_USERNAME_1);
	        		event.setUuid(NEW_ACCOUNT_UUID);
	        		service.insertAccount(event);
	            }
	    );
	    assertEquals("Account username already exists", exception.getMessage());
		
    }
	
	@Test
	public void testFindByUsername() throws Exception {
		
		Account foundAccount = service.findOneByUsernameOrElseThrowException(DB_ACCOUNT_USERNAME_2);
		
		assertEquals(DB_ACCOUNT_ID_2, foundAccount.getId()); 
    }
	
	@Test
	public void testFindDTOByUsernameException() throws Exception {
		
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	        		Account foundAccount = service.findOneByUsernameOrElseThrowException(DB_ACCOUNT_USERNAME_DoesntExist);
	            }
	    );
	    assertEquals("Account not found", exception.getMessage());
    
    }
}
