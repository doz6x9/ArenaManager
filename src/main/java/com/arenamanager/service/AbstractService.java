package com.arenamanager.service;

import com.arenamanager.AbstractLayerComponent;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class AbstractService implements AbstractLayerComponent {

    protected abstract String serviceName();

    @Override
    public String componentName() {
        return "service:" + serviceName();
    }

    protected <T> T requireFound(Optional<T> value, Supplier<? extends RuntimeException> exceptionSupplier) {
        return value.orElseThrow(exceptionSupplier);
    }
}
