package com.arenamanager.dto;

import java.time.Instant;

public record AuditLogResponseDto(
        Long id,
        Instant createdAt,
        String action,
        String actor,
        String details,
        Long tournamentId,
        String tournamentName,
        Long matchId
) implements AbstractResponseDto {

    @Override
    public Long id() {
        return id;
    }
}
