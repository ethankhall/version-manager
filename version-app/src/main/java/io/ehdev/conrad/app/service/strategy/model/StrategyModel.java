package io.ehdev.conrad.app.service.strategy.model;

import io.ehdev.conrad.database.model.VersionBumperModel;

public class StrategyModel {
    final String name;
    final String description;

    public StrategyModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public StrategyModel(VersionBumperModel versionBumperModel) {
        this(versionBumperModel.getBumperName(), versionBumperModel.getDescription());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
