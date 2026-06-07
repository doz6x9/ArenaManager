package com.arenamanager.repository;

import com.arenamanager.domain.Player;

import java.util.Optional;

public interface PlayerRepository extends AbstractRepository<Player> {

    Optional<Player> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
