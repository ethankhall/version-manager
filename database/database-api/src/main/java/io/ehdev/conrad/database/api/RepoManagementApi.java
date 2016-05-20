package io.ehdev.conrad.database.api;


import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.repo.RepoCreateModel;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RepoManagementApi {

    ApiRepoDetailsModel createRepo(RepoCreateModel repoCreateModel);

    Optional<ApiCommitModel> findLatestCommit(ResourceDetails resourceDetails, List<ApiCommitModel> history);

    void createCommit(ResourceDetails resourceDetails, ApiCommitModel nextVersion, ApiCommitModel parent);

    Optional<ApiCommitModel> findCommit(ResourceDetails resourceDetails, String apiCommit);

    Map<String, ApiRepoDetailsModel> getAllRepos();

    List<ApiCommitModel> findAllCommits(ResourceDetails resourceDetails);

    Optional<ApiRepoDetailsModel> getDetails(ResourceDetails resourceDetails);

    boolean doesRepoExist(ResourceDetails resourceDetails);

    void delete(ResourceDetails resourceDetails);
}
