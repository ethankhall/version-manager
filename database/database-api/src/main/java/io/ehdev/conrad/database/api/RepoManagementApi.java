package io.ehdev.conrad.database.api;


import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.project.commit.ApiFullCommitModel;

import java.util.List;
import java.util.Optional;

public interface RepoManagementApi {
    ApiRepoModel createRepo(String projectName, String repoName, String bumperName, String repoUrl);

    Optional<ApiFullCommitModel> findLatestCommit(String projectName, String repoName, List<ApiCommitModel> history);

    void createCommit(String projectName, String repoName, ApiFullCommitModel nextVersion, ApiCommitModel parent);

    Optional<ApiFullCommitModel> findCommit(String projectName, String repoName, ApiFullCommitModel commit);

    List<ApiRepoModel> getAll();

    List<ApiFullCommitModel> findAllCommits(String projectName, String repoName);

    Optional<ApiRepoDetailsModel> getDetails(String projectName, String repoName);

    boolean doesRepoExist(String projectName, String repoName);
}
