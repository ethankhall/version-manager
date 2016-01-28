package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.project.ApiQualifiedRepoModel;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.project.commit.ApiFullCommitModel;

import java.util.List;
import java.util.Optional;

public class TestDoubleRepoManagementApi implements RepoManagementApi {

    

    @Override
    public ApiRepoModel createRepo(ApiQualifiedRepoModel qualifiedRepo, String bumperName, String repoUrl) {
        return null;
    }

    @Override
    public Optional<ApiFullCommitModel> findLatestCommit(ApiQualifiedRepoModel qualifiedRepo, List<ApiCommitModel> history) {
        return null;
    }

    @Override
    public void createCommit(ApiQualifiedRepoModel qualifiedRepo, ApiFullCommitModel nextVersion, ApiCommitModel parent) {

    }

    @Override
    public Optional<ApiFullCommitModel> findCommit(ApiQualifiedRepoModel qualifiedRepo, String apiCommit) {
        return null;
    }

    @Override
    public List<ApiRepoModel> getAll() {
        return null;
    }

    @Override
    public List<ApiFullCommitModel> findAllCommits(ApiQualifiedRepoModel qualifiedRepo) {
        return null;
    }

    @Override
    public Optional<ApiRepoDetailsModel> getDetails(ApiQualifiedRepoModel qualifiedRepo) {
        return null;
    }

    @Override
    public boolean doesRepoExist(ApiQualifiedRepoModel qualifiedRepo) {
        return false;
    }
}
