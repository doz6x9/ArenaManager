package com.arenamanager.dto;

public record TeamSummaryDto(
        Long id,
        String name,
        String tag
) implements AbstractResponseDto {

    @Override
    public Long id() {
        return id;
    }
}
