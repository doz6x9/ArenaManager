package com.arenamanager.dto;

public record PlayerResponseDto(
        Long id,
        String username,
        String email,
        Long teamId,
        String teamName,
        PlayerProfileDto profile
) {
}
