package io.ehdev.conrad.app.service;

import io.ehdev.conrad.backend.version.commit.CommitVersion;
import io.ehdev.conrad.database.model.CommitModel;
import io.ehdev.conrad.database.model.VcsRepoModel;
import io.ehdev.conrad.database.model.VersionBumperModel;
import io.ehdev.conrad.model.repo.RepoResponseModel;
import io.ehdev.conrad.model.strategy.StrategyModel;
import io.ehdev.conrad.model.version.UncommitedVersionModel;
import io.ehdev.conrad.model.version.VersionCommitModel;

public class ApiFactory {
    public static class StrategyModelFactory {
        public static StrategyModel create(VersionBumperModel versionBumperModel) {
            return new StrategyModel(versionBumperModel.getBumperName(), versionBumperModel.getDescription());
        }
    }

    public static class RepoModelFactory {
        public static RepoResponseModel create(VcsRepoModel repo) {
            return new RepoResponseModel(repo.getRepoName(), repo.getUrl(), repo.getVersionBumperName(), repo.getIdAsString(), repo.getToken());
        }
    }

    public static class VersionModelFactory {
        public static VersionCommitModel create(String commit, CommitVersion commitVersion) {
            return new VersionCommitModel(commit, commitVersion.toVersionString(), commitVersion.getVersionGroup(), commitVersion.getPostFix());
        }

        public static UncommitedVersionModel create(CommitVersion commitVersion) {
            return new UncommitedVersionModel(commitVersion.toVersionString(), commitVersion.getVersionGroup(), commitVersion.getPostFix());
        }

        public static VersionCommitModel create(CommitModel commitModel) {
            return create(commitModel.getCommitId(), commitModel.getVersion());
        }
    }
}
