package com.arenamanager.dto;

import com.arenamanager.AbstractLayerComponent;

public interface AbstractResponseDto extends AbstractLayerComponent {

    Long id();

    @Override
    default String componentName() {
        return "response-dto";
    }
}
