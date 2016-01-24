package io.ehdev.conrad.database.api;

import io.ehdev.conrad.model.internal.ApiCommit;
import io.ehdev.conrad.model.internal.ApiRepo;

import java.util.List;
import java.util.Optional;

public interface RepoManagementApi {
    ApiRepo createRepo(String projectName, String repoName, String bumperName, String repoUrl);

    Optional<ApiCommit> findLatestCommit(String projectName, String repoName, List<ApiCommit> history);

    void createCommit(String projectName, String repoName, ApiCommit nextVersion, List<ApiCommit> history);

    Optional<ApiCommit> findCommit(String projectName, String repoName, ApiCommit commit);

    List<ApiRepo> getAll();

    List<ApiCommit> findAllCommits(String projectName, String repoName);
}
