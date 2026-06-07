package com.arenamanager.controller.rest;

import com.arenamanager.AbstractLayerComponent;

public abstract class AbstractRestController implements AbstractLayerComponent {

    protected abstract String resourceName();

    @Override
    public String componentName() {
        return "rest-controller:" + resourceName();
    }
}
