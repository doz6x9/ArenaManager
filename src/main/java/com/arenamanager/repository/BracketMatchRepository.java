package com.arenamanager.repository;

import com.arenamanager.domain.BracketMatch;
import com.arenamanager.domain.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BracketMatchRepository extends JpaRepository<BracketMatch, Long> {

    List<BracketMatch> findByTournamentIdOrderByRoundNumberAscIdAsc(Long tournamentId);

    long countByStatus(MatchStatus status);
}
