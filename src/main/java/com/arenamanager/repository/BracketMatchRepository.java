package com.arenamanager.repository;

import com.arenamanager.domain.BracketMatch;
import com.arenamanager.domain.MatchStatus;

import java.util.List;

public interface BracketMatchRepository extends AbstractRepository<BracketMatch> {

    List<BracketMatch> findByTournamentIdOrderByRoundNumberAscIdAsc(Long tournamentId);

    long countByStatus(MatchStatus status);
}
