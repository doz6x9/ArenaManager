package com.arenamanager.exception;

import com.arenamanager.AbstractLayerComponent;

public abstract class AbstractArenaException extends RuntimeException implements AbstractLayerComponent {

    protected AbstractArenaException(String message) {
        super(message);
    }

    @Override
    public String componentName() {
        return "exception:" + getClass().getSimpleName();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
