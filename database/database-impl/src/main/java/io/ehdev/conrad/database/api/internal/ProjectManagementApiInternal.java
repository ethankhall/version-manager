package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.ProjectManagementApi;
import io.ehdev.conrad.db.tables.pojos.ProjectDetails;

import java.util.Optional;

public interface ProjectManagementApiInternal extends ProjectManagementApi {
    Optional<ProjectDetails> findProject(String projectName);
}
