package com.arenamanager.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record TournamentRequestDto(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 80) String gameTitle,
        @Min(2) @Max(128) int maxTeams,
        boolean registrationOpen,
        Set<Long> teamIds
) {
}
