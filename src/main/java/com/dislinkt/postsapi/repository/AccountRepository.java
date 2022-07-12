package com.dislinkt.postsapi.repository;

import com.dislinkt.postsapi.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findOneByUuid(String uuid);

    Optional<Account> findOneByUsername(String username);
}