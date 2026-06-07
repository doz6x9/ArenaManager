package com.arenamanager.repository;

import com.arenamanager.domain.UserAccount;

import java.util.Optional;

public interface UserAccountRepository extends AbstractRepository<UserAccount> {

    Optional<UserAccount> findByUsername(String username);

    boolean existsByUsername(String username);
}
