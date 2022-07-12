package com.dislinkt.postsapi.web.rest.account;

import com.dislinkt.postsapi.service.account.AccountService;
import com.dislinkt.postsapi.util.ReturnResponse;
import com.dislinkt.postsapi.web.rest.account.payload.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountResource {

    @Autowired
    private AccountService accountService;

    // only for accounts api
    @PostMapping
    private ResponseEntity<AccountDTO> insertAccount(@RequestBody AccountDTO accountDTO) {
        return ReturnResponse.entityCreated(accountService.insertAccount(accountDTO));
    }
}
