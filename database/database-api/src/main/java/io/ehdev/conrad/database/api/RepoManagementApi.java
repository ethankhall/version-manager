package io.ehdev.conrad.database.api;


import io.ehdev.conrad.database.model.project.ApiQualifiedRepoModel;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.project.commit.ApiFullCommitModel;

import java.util.List;
import java.util.Optional;

public interface RepoManagementApi {
    ApiRepoModel createRepo(ApiQualifiedRepoModel qualifiedRepo, String bumperName, String repoUrl);

    Optional<ApiFullCommitModel> findLatestCommit(ApiQualifiedRepoModel qualifiedRepo, List<ApiCommitModel> history);

    void createCommit(ApiQualifiedRepoModel qualifiedRepo, ApiFullCommitModel nextVersion, ApiCommitModel parent);

    Optional<ApiFullCommitModel> findCommit(ApiQualifiedRepoModel qualifiedRepo, ApiFullCommitModel commit);

    List<ApiRepoModel> getAll();

    List<ApiFullCommitModel> findAllCommits(ApiQualifiedRepoModel qualifiedRepo);

    Optional<ApiRepoDetailsModel> getDetails(ApiQualifiedRepoModel qualifiedRepo);

    boolean doesRepoExist(ApiQualifiedRepoModel qualifiedRepo);
}
