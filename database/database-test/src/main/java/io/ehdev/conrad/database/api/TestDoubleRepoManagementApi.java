package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.project.ApiFullRepoModel;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestDoubleRepoManagementApi implements RepoManagementApi {

    private final ApiVersionBumperModel bumperModel;
    private Map<String, ApiRepoDetailsModel> storage = new HashMap<>();
    private Map<String, List<ApiCommitModel>> commits = new HashMap<>();

    public TestDoubleRepoManagementApi(ApiVersionBumperModel bumperModel) {
        this.bumperModel = bumperModel;
    }

    @Override
    public ApiRepoDetailsModel createRepo(ApiFullRepoModel qualifiedRepo, String bumperName, boolean ignored) {
        storage.put(qualifiedRepo.getMergedName(), new ApiRepoDetailsModel(qualifiedRepo, bumperModel));
        commits.put(qualifiedRepo.getMergedName(), new ArrayList<>());
        return storage.get(qualifiedRepo.getMergedName());
    }

    @Override
    public Optional<ApiCommitModel> findLatestCommit(ApiRepoModel qualifiedRepo, List<ApiCommitModel> history) {
        List<ApiCommitModel> size = commits.get(qualifiedRepo.getMergedName());
        Map<String, ApiCommitModel> versions = size.stream().collect(Collectors.toMap(ApiCommitModel::getCommitId, Function.identity()));
        for(int i = history.size() - 1; i >= 0; i--) {
            ApiCommitModel apiCommitModel = history.get(i);
            if(versions.containsKey(apiCommitModel.getCommitId())) {
                return Optional.of(versions.get(apiCommitModel.getCommitId()));
            }
        }
        return Optional.empty();
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

    @Override
    public void delete(ApiParameterContainer apiParameterContainer) {
        
    }
}
