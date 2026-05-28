package com.arenamanager.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TeamRequestDto(
        @NotBlank @Size(max = 80) String name,
        @NotBlank @Size(max = 12) String tag,
        @Min(1) @Max(10) int maxRosterSize
) {
}
