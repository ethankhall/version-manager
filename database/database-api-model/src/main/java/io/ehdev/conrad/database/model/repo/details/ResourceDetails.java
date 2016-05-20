package io.ehdev.conrad.database.model.repo.details;

public class ResourceDetails {

    private final ResourceId projectId;
    private final ResourceId repoId;

    public ResourceDetails(ResourceId projectId, ResourceId repoId) {
        this.projectId = projectId;
        this.repoId = repoId;
    }

    public ResourceDetails(ResourceId projectId) {
        this(projectId, null);
    }

    public ResourceId getProjectId() {
        return projectId;
    }

    public ResourceId getRepoId() {
        return repoId;
    }
}
