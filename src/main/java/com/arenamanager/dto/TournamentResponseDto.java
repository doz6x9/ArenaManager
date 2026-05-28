package com.arenamanager.dto;

import java.util.List;

public record TournamentResponseDto(
        Long id,
        String name,
        String gameTitle,
        int maxTeams,
        boolean registrationOpen,
        List<TeamSummaryDto> registeredTeams,
        List<MatchResponseDto> matches
) {
}
