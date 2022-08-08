package com.dislinkt.postsapi.service.account;

import com.dislinkt.postsapi.domain.account.Account;
import com.dislinkt.postsapi.event.AccountCreatedEvent;
import com.dislinkt.postsapi.exception.types.EntityAlreadyExistsException;
import com.dislinkt.postsapi.exception.types.EntityNotFoundException;
import com.dislinkt.postsapi.repository.AccountRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @RabbitListener(queues = {"q.account-registration-posts"})
    public void insertAccount(AccountCreatedEvent accountCreatedEvent) {

        Optional<Account> accountOrEmpty = accountRepository.findOneByUsername(accountCreatedEvent.getUsername());

        if (accountOrEmpty.isPresent()) {
            throw new EntityAlreadyExistsException("Account username already exists");
        }

        Account account = new Account();

        account.setName(accountCreatedEvent.getName());
        account.setUsername(accountCreatedEvent.getUsername());

        accountRepository.save(account);
    }

    public Account findOneByUuidOrElseThrowException(String uuid) {
        return accountRepository.findOneByUuid(uuid).orElseThrow(() ->
                new EntityNotFoundException("Account not found"));
    }
}
