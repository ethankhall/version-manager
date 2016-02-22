package io.ehdev.conrad.database.model.permission;

import java.util.UUID;

public class ProjectApiAuthentication implements ApiTokenAuthentication {
    private final String projectName;
    private final UUID uuid;

    public ProjectApiAuthentication(UUID uuid, String projectName) {
        this.uuid = uuid;
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getNiceName() {
        return projectName;
    }
}
