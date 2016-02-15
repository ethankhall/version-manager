package io.ehdev.conrad.database.api;


import io.ehdev.conrad.database.model.project.ApiFullRepoModel;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RepoManagementApi {
    ApiRepoDetailsModel createRepo(ApiFullRepoModel apiParameterContainer, String bumperName, String repoUrl);

    Optional<ApiCommitModel> findLatestCommit(ApiRepoModel qualifiedRepo, List<ApiCommitModel> history);

    void createCommit(ApiRepoModel qualifiedRepo, ApiCommitModel nextVersion, ApiCommitModel parent);

    Optional<ApiCommitModel> findCommit(ApiRepoModel qualifiedRepo, String apiCommit);

    Map<String, ApiRepoDetailsModel> getAllRepos();

    List<ApiCommitModel> findAllCommits(ApiRepoModel qualifiedRepo);

    Optional<ApiRepoDetailsModel> getDetails(ApiRepoModel qualifiedRepo);

    boolean doesRepoExist(ApiRepoModel qualifiedRepo);
}
