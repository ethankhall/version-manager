package io.ehdev.conrad.database.api;


import io.ehdev.conrad.database.model.PrimaryResourceData;

import java.util.Optional;
import java.util.UUID;

public interface PrimaryKeySearchApi {

    Optional<PrimaryResourceData> findResourceDataByProjectId(UUID projectId);

    Optional<PrimaryResourceData> findResourceDataByRepoId(UUID repoId);

    Optional<UUID> findProjectId(String projectName);

    Optional<UUID> findRepoId(String projectName, String repoName);
}
