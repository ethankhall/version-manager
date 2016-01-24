package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.api.exception.BumperNotFoundException;
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
import io.ehdev.conrad.model.internal.ApiCommit;
import io.ehdev.conrad.model.internal.ApiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ApiRepo createRepo(String projectName, String repoName, String bumperName, String repoUrl) {
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

    public Optional<ApiCommit> findCommit(String projectName, String repoName, ApiCommit commit) {
        return Optional.ofNullable(commitModelRepository.findByCommitId(projectName, repoName, commit.getCommitId())).map(ModelConversionUtility::toApiModel);
    }

    public Optional<ApiCommit> findLatestCommit(String projectName, String repoName, List<ApiCommit> history) {
        return findCommitModelInternal(projectName, repoName, history).findFirst().map(ModelConversionUtility::toApiModel);
    }

    private Stream<CommitModel> findCommitModelInternal(String projectName, String repoName, List<ApiCommit> history) {
        RepoModel repo = repoModelRepository.findByProjectNameAndRepoName(projectName, repoName);
        List<CommitModel> models = commitModelRepository.findMatchingCommits(repo, history.stream().map(ApiCommit::getCommitId).collect(Collectors.toList()));
        return models.stream().sorted();
    }

    public void createCommit(String projectName, String repoName, ApiCommit nextVersion, List<ApiCommit> history) {
        CommitModel parentModel = null;
        if(history != null && !history.isEmpty()) {
            parentModel = findCommitModelInternal(projectName, repoName, history).findFirst().orElse(null);
        }
        RepoModel repo = repoModelRepository.findByProjectNameAndRepoName(projectName, repoName);
        commitModelRepository.save(new CommitModel(nextVersion.getCommitId(), repo, nextVersion.getVersion(), parentModel));
    }

    @Override
    public List<ApiRepo> getAll() {
        return repoModelRepository.findAll().stream().map(ModelConversionUtility::toApiModel).collect(Collectors.toList());
    }

    @Override
    public List<ApiCommit> findAllCommits(String projectName, String repoName) {
        return commitModelRepository
            .findAllByProjectNameAndRepoName(projectName, repoName)
            .stream()
            .map(ModelConversionUtility::toApiModel)
            .collect(Collectors.toList());
    }

}
