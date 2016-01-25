package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.api.exception.BumperNotFoundException;
import io.ehdev.conrad.database.api.exception.CommitNotFoundException;
import io.ehdev.conrad.database.api.exception.ProjectNotFoundException;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.impl.bumper.VersionBumperModel;
import io.ehdev.conrad.database.impl.bumper.VersionBumperModelRepository;
import io.ehdev.conrad.database.impl.commit.CommitModel;
import io.ehdev.conrad.database.impl.commit.CommitModelRepository;
import io.ehdev.conrad.database.impl.project.ProjectModel;
import io.ehdev.conrad.database.impl.project.ProjectModelRepository;
import io.ehdev.conrad.database.impl.repo.RepoModel;
import io.ehdev.conrad.database.impl.repo.RepoModelRepository;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.project.commit.ApiFullCommitModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DefaultRepoManagementApi implements RepoManagementApi {

    private final VersionBumperModelRepository bumperModelRepository;
    private final ProjectModelRepository projectModelRepository;
    private final RepoModelRepository repoModelRepository;
    private final CommitModelRepository commitModelRepository;

    public static final Comparator<CommitModel> REVERSE_ORDER = Comparator.<CommitModel>reverseOrder();

    @Autowired
    public DefaultRepoManagementApi(VersionBumperModelRepository bumperModelRepository,
                                    ProjectModelRepository projectModelRepository,
                                    RepoModelRepository repoModelRepository,
                                    CommitModelRepository commitModelRepository) {
        this.bumperModelRepository = bumperModelRepository;
        this.projectModelRepository = projectModelRepository;
        this.repoModelRepository = repoModelRepository;
        this.commitModelRepository = commitModelRepository;
    }

    @Override
    public ApiRepoModel createRepo(String projectName, String repoName, String bumperName, String repoUrl) {
        VersionBumperModel bumper = bumperModelRepository.findByBumperName(bumperName);
        if(bumper == null) {
            throw new BumperNotFoundException(bumperName);
        }

        ProjectModel project = projectModelRepository.findByProjectName(projectName);
        if(project == null) {
            throw new ProjectNotFoundException(projectName);
        }
        RepoModel save = repoModelRepository.save(new RepoModel(repoName, repoUrl, bumper, project));
        return ModelConversionUtility.toApiModel(save);
    }

    @Override
    public Optional<ApiFullCommitModel> findCommit(String projectName, String repoName, ApiFullCommitModel apiCommit) {
        CommitModel commitModel = commitModelRepository.findByCommitId(projectName, repoName, apiCommit.getCommitId());
        return Optional.ofNullable(commitModel).map(ModelConversionUtility::toApiModel);
    }

    @Override
    public Optional<ApiFullCommitModel> findLatestCommit(String projectName, String repoName, List<ApiCommitModel> history) {
        return findCommitModelInternal(projectName, repoName, history).findFirst().map(ModelConversionUtility::toApiModel);
    }

    private Stream<CommitModel> findCommitModelInternal(String projectName, String repoName, List<ApiCommitModel> history) {
        RepoModel repo = repoModelRepository.findByProjectNameAndRepoName(projectName, repoName);
        List<CommitModel> models = commitModelRepository
            .findMatchingCommits(repo,
                history.stream()
                    .map(ApiCommitModel::getCommitId)
                    .collect(Collectors.toList()));

        return models.stream().sorted(REVERSE_ORDER);
    }

    @Override
    public void createCommit(String projectName, String repoName, ApiFullCommitModel nextVersion, ApiCommitModel history) {
        CommitModel parentModel = null;
        if(history != null) {
            parentModel = commitModelRepository.findByCommitId(projectName, repoName, history.getCommitId());
            if(parentModel == null) {
                throw new CommitNotFoundException(history.getCommitId());
            }
        }
        RepoModel repo = repoModelRepository.findByProjectNameAndRepoName(projectName, repoName);
        commitModelRepository.save(new CommitModel(nextVersion.getCommitId(), repo, nextVersion.getVersion(), parentModel));
    }

    @Override
    public List<ApiRepoModel> getAll() {
        return repoModelRepository
            .findAll()
            .stream()
            .map(ModelConversionUtility::toApiModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<ApiFullCommitModel> findAllCommits(String projectName, String repoName) {
        return commitModelRepository
            .findAllByProjectNameAndRepoName(projectName, repoName)
            .stream()
            .sorted(REVERSE_ORDER)
            .map(ModelConversionUtility::toApiModel)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<ApiRepoDetailsModel> getDetails(String projectName, String repoName) {
        RepoModel repo = repoModelRepository.findByProjectNameAndRepoName(projectName, repoName);
        return Optional
            .ofNullable(repo)
            .map(it -> new ApiRepoDetailsModel(
                ModelConversionUtility.toApiModel(it),
                ModelConversionUtility.toApiModel(it.getVersionBumperModel())
            ));
    }

    @Override
    public boolean doesRepoExist(String projectName, String repoName) {
        return repoModelRepository.doesRepoExist(projectName, repoName);
    }

}
