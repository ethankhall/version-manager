package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;

import java.util.*;

public class TestDoubleRepoManagementApi implements RepoManagementApi {

    private final ApiVersionBumperModel bumperModel;
    private Map<String, ApiRepoDetailsModel> storage = new HashMap<>();
    private Map<String, List<ApiCommitModel>> commits = new HashMap<>();

    public TestDoubleRepoManagementApi(ApiVersionBumperModel bumperModel) {
        this.bumperModel = bumperModel;
    }

    @Override
    public ApiRepoDetailsModel createRepo(ApiRepoModel qualifiedRepo, String bumperName, String repoUrl) {
        storage.put(qualifiedRepo.getMergedName(), new ApiRepoDetailsModel(qualifiedRepo, bumperModel));
        commits.put(qualifiedRepo.getMergedName(), new ArrayList<>());
        return storage.get(qualifiedRepo.getMergedName());
    }

    @Override
    public Optional<ApiCommitModel> findLatestCommit(ApiRepoModel qualifiedRepo, List<ApiCommitModel> history) {
        int size = commits.get(qualifiedRepo.getMergedName()).size();
        return Optional.ofNullable(commits.get(qualifiedRepo.getMergedName()).get(size - 1));
    }

    @Override
    public void createCommit(ApiRepoModel qualifiedRepo, ApiCommitModel nextVersion, ApiCommitModel parent) {
        commits.get(qualifiedRepo.getMergedName()).add(nextVersion);
    }

    @Override
    public Optional<ApiCommitModel> findCommit(ApiRepoModel qualifiedRepo, String apiCommit) {
        return commits.get(qualifiedRepo.getMergedName())
            .stream()
            .filter(apiCommitModel -> Objects.equals(apiCommitModel.getCommitId(), apiCommit))
            .findFirst();
    }

    @Override
    public Map<String, ApiRepoDetailsModel> getAllRepos() {
        return storage;
    }

    @Override
    public List<ApiCommitModel> findAllCommits(ApiRepoModel qualifiedRepo) {
        return commits.get(qualifiedRepo.getMergedName());
    }

    @Override
    public Optional<ApiRepoDetailsModel> getDetails(ApiRepoModel qualifiedRepo) {
        return Optional.ofNullable(storage.get(qualifiedRepo.getMergedName()));
    }

    @Override
    public boolean doesRepoExist(ApiRepoModel qualifiedRepo) {
        return storage.containsKey(qualifiedRepo.getMergedName());
    }

}
