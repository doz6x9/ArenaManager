package com.arenamanager.mapper;

public interface AbstractMapper {

    default String mapperName() {
        return getClass().getSimpleName();
    }
}
