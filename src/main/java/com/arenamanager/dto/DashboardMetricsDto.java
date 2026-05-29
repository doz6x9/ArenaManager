package com.arenamanager.dto;

public record DashboardMetricsDto(
        long tournamentCount,
        long teamCount,
        long playerCount,
        long scheduledMatches,
        long inProgressMatches,
        long completedMatches,
        long auditEventCount
) {
}
