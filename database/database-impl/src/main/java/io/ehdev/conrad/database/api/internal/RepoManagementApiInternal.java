package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.db.tables.pojos.RepoDetails;

import java.util.Optional;

public interface RepoManagementApiInternal extends RepoManagementApi {
    Optional<RepoDetails> findRepository(String projectName, String repoName);
}
