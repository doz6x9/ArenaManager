package com.arenamanager.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PlayerRequestDto(
        @NotBlank @Size(max = 60) String username,
        @NotBlank @Email @Size(max = 120) String email,
        Long teamId,
        @Valid PlayerProfileDto profile
) {
}
