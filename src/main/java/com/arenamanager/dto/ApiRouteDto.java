package com.arenamanager.dto;

public record ApiRouteDto(
        String method,
        String path,
        String area,
        String access,
        String description
) {
}
