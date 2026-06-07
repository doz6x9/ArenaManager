package com.arenamanager.service;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class AbstractService {

    protected abstract String serviceName();

    protected <T> T requireFound(Optional<T> value, Supplier<? extends RuntimeException> exceptionSupplier) {
        return value.orElseThrow(exceptionSupplier);
    }
}
