package com.arenamanager.exception;

public class TournamentFullException extends RuntimeException {

    public TournamentFullException(String message) {
        super(message);
    }
}
