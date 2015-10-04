package io.ehdev.version.service.model;

import java.util.Map;

public class StrategyResponse {
    Map<String, Strategies> strategies;

    public Map<String, Strategies> getStrategies() {
        return strategies;
    }

    public void setStrategies(Map<String, Strategies> strategies) {
        this.strategies = strategies;
    }

    public static class Strategies {
        final String name;
        final String description;

        public Strategies(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }
}
