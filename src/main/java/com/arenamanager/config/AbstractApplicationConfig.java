package com.arenamanager.config;

import com.arenamanager.AbstractLayerComponent;

public abstract class AbstractApplicationConfig implements AbstractLayerComponent {

    protected abstract String configName();

    @Override
    public String componentName() {
        return "config:" + configName();
    }

    protected boolean shouldSeedDemoData(boolean enabled, long existingRows) {
        return enabled && existingRows == 0;
    }
}
