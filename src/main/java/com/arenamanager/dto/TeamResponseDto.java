package com.arenamanager.dto;

import java.util.List;

public record TeamResponseDto(
        Long id,
        String name,
        String tag,
        int maxRosterSize,
        int rosterCount,
        List<PlayerResponseDto> players
) {
}
