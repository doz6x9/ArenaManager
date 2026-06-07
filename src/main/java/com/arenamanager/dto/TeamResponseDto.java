package com.arenamanager.dto;

import java.util.List;

public record TeamResponseDto(
        Long id,
        String name,
        String tag,
        int maxRosterSize,
        int rosterCount,
        List<PlayerResponseDto> players
) implements AbstractResponseDto {

    @Override
    public Long id() {
        return id;
    }
}
