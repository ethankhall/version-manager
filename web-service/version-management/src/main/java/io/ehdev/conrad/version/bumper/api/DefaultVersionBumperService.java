package io.ehdev.conrad.version.bumper.api;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.version.bumper.VersionBumper;
import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.details.DefaultCommitDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
    public CommitVersion findNextVersion(ResourceDetails repoModel,
                                         String commitId,
                                         String message,
                                         ApiCommitModel lastCommit) {

        Optional<ApiRepoDetailsModel> details = repoManagementApi.getDetails(repoModel);
        ApiRepoDetailsModel apiModel = details.get();

        VersionBumper versionBumper = findVersionBumper(apiModel.getBumper().getClassName());
        DefaultCommitDetails commitDetails = new DefaultCommitDetails(commitId, message);
        return versionBumper.createNextVersion(lastCommit.getVersion(), commitDetails);
    }
}
