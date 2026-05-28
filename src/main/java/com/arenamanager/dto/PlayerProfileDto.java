package com.arenamanager.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record PlayerProfileDto(
        @Min(100) @Max(32000) Integer preferredPeripheralDpi,
        @Size(max = 60) String mouseGripStyle,
        @Size(max = 1000) String bio
) {
}
