package io.ehdev.conrad.database.api.v2;


import io.ehdev.conrad.database.api.v2.details.ResourceDetails;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RepoManagementApiV2 {
    ApiRepoDetailsModel createRepo(ResourceDetails resourceDetails, String bumperName, boolean isPublic);

    Optional<ApiCommitModel> findLatestCommit(ResourceDetails resourceDetails, List<ApiCommitModel> history);

    void createCommit(ResourceDetails resourceDetails, ApiCommitModel nextVersion, ApiCommitModel parent);

    Optional<ApiCommitModel> findCommit(ResourceDetails resourceDetails, String apiCommit);

    Map<String, ApiRepoDetailsModel> getAllRepos();

    List<ApiCommitModel> findAllCommits(ResourceDetails resourceDetails);

    Optional<ApiRepoDetailsModel> getDetails(ResourceDetails resourceDetails);

    boolean doesRepoExist(ResourceDetails resourceDetails);

    void delete(ResourceDetails resourceDetails);
}
