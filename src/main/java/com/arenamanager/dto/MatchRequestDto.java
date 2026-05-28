package com.arenamanager.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MatchRequestDto(
        @NotNull Long tournamentId,
        Long homeTeamId,
        Long awayTeamId,
        @Min(1) int roundNumber,
        @Min(1) @Max(7) int bestOf
) {
}
