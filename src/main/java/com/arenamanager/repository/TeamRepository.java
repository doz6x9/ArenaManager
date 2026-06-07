package com.arenamanager.repository;

import com.arenamanager.domain.Team;

public interface TeamRepository extends AbstractRepository<Team> {

    boolean existsByName(String name);

    boolean existsByTag(String tag);
}
