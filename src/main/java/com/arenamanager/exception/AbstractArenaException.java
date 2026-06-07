package com.arenamanager.exception;

public abstract class AbstractArenaException extends RuntimeException {

    protected AbstractArenaException(String message) {
        super(message);
    }
}
