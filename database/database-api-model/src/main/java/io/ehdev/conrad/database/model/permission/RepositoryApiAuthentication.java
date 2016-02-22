package io.ehdev.conrad.database.model.permission;

import java.util.UUID;

public class RepositoryApiAuthentication implements ApiTokenAuthentication {
    private final String projectName;
    private final String repoName;
    private final UUID uuid;

    public RepositoryApiAuthentication(UUID uuid, String projectName, String repoName) {
        this.projectName = projectName;
        this.repoName = repoName;
        this.uuid = uuid;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getRepoName() {
        return repoName;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getNiceName() {
        return projectName + "/" + repoName;
    }
}
