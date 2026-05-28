package com.arenamanager.dto;

import jakarta.validation.constraints.Min;

public record ScoreUpdateRequestDto(
        @Min(0) int homeScore,
        @Min(0) int awayScore
) {
}
