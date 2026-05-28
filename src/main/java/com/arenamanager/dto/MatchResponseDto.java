package com.arenamanager.dto;

public record MatchResponseDto(
        Long id,
        Long tournamentId,
        TeamSummaryDto homeTeam,
        TeamSummaryDto awayTeam,
        TeamSummaryDto winnerTeam,
        int roundNumber,
        int bestOf,
        int homeScore,
        int awayScore,
        String status
) {
}
