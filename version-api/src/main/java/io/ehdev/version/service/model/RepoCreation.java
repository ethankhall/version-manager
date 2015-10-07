package io.ehdev.version.service.model;

public class RepoCreation {
    String repoName;
    String strategyName;

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    @Override
    public String toString() {
        return "RepoCreation{" +
            "repoName='" + repoName + '\'' +
            ", strategyName='" + strategyName + '\'' +
            '}';
    }
}
