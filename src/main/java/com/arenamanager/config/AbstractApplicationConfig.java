package com.arenamanager.config;

public abstract class AbstractApplicationConfig {

    protected abstract String configName();

    protected boolean shouldSeedDemoData(boolean enabled, long existingRows) {
        return enabled && existingRows == 0;
    }
}
