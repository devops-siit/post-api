package com.dislinkt.postsapi.service.account;

import com.dislinkt.postsapi.domain.account.Account;
import com.dislinkt.postsapi.exception.types.EntityAlreadyExistsException;
import com.dislinkt.postsapi.exception.types.EntityNotFoundException;
import com.dislinkt.postsapi.repository.AccountRepository;
import com.dislinkt.postsapi.web.rest.account.payload.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountDTO insertAccount(AccountDTO accountDTO) {

        Optional<Account> accountOrEmpty = accountRepository.findOneByUsername(accountDTO.getUsername());

        if (accountOrEmpty.isPresent()) {
            throw new EntityAlreadyExistsException("Account username already exists");
        }

        Account account = new Account();

        account.setName(accountDTO.getName());
        account.setUsername(accountDTO.getUsername());

        accountRepository.save(account);

        accountDTO.setUuid(account.getUuid());

        return accountDTO;
    }

    public Account findOneByUuidOrElseThrowException(String uuid) {
        return accountRepository.findOneByUuid(uuid).orElseThrow(() ->
                new EntityNotFoundException("Account not found"));
    }
}
