package io.ehdev.conrad.version.bumper.api;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.api.exception.CommitNotFoundException;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.version.bumper.VersionBumper;
import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.internal.DefaultCommitDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DefaultVersionBumperService implements VersionBumperService {

    private final Map<String, VersionBumper> versionBumpers = new HashMap<>();
    private final RepoManagementApi repoManagementApi;

    @Autowired
    public DefaultVersionBumperService(final Set<VersionBumper> bumperSet,
                                       RepoManagementApi repoManagementApi) {
        this.repoManagementApi = repoManagementApi;
        bumperSet.forEach(bumper -> versionBumpers.put(bumper.getClass().getName(), bumper));
    }

    @Override
    public VersionBumper findVersionBumper(String className) {
        return Optional
            .ofNullable(versionBumpers.get(className))
            .orElseThrow(() -> new RuntimeException("Unknown bumper " + className));
    }

    @Override
    public CommitVersion findNextVersion(ApiRepoModel repoModel,
                                         String commitId,
                                         String message,
                                         CommitVersion lastCommit) {

        ApiRepoDetailsModel apiModel = repoManagementApi.getDetails(repoModel).get();

        VersionBumper versionBumper = findVersionBumper(apiModel.getBumper().getClassName());
        DefaultCommitDetails commitDetails = new DefaultCommitDetails(commitId, message);
        return versionBumper.createNextVersion(lastCommit, commitDetails);
    }

    @Override
    public ApiCommitModel findLatestCommitVersion(ApiRepoModel repoModel, List<ApiCommitModel> history) {
        if(!history.isEmpty()) {
            Optional<ApiCommitModel> latestCommit = repoManagementApi.findLatestCommit(repoModel, history);
            if(!latestCommit.isPresent()) {
                String joinedCommit = history.stream().map(ApiCommitModel::getCommitId).reduce((t, u) -> t + "," + u).get();
                throw new CommitNotFoundException(joinedCommit);
            } else {
                return latestCommit.get();
            }
        } else {
            return null;
        }
    }
}
