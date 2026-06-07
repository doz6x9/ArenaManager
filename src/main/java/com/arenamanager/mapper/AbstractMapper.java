package com.arenamanager.mapper;

import com.arenamanager.AbstractLayerComponent;

public interface AbstractMapper extends AbstractLayerComponent {

    default String mapperName() {
        return getClass().getSimpleName();
    }

    @Override
    default String componentName() {
        return "mapper:" + mapperName();
    }
}
