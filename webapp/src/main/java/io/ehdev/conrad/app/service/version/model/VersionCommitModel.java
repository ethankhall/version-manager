package io.ehdev.conrad.app.service.version.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.ehdev.conrad.backend.version.commit.CommitVersion;
import io.ehdev.conrad.database.model.CommitModel;

import java.util.List;

public class VersionCommitModel {

    @JsonView(VersionView.ReleasedVersionView.class)
    final String commit;

    @JsonView(VersionView.UnreleasedVersionView.class)
    final String version;

    @JsonView(VersionView.UnreleasedVersionView.class)
    final List<Integer> group;

    @JsonView(VersionView.UnreleasedVersionView.class)
    final String postfix;

    public VersionCommitModel(String commit, CommitVersion commitVersion) {
        this.commit = commit;
        this.version = commitVersion.toVersionString();
        this.group = commitVersion.getVersionGroup();
        this.postfix = commitVersion.getPostFix();
    }

    public VersionCommitModel(CommitModel commitModel) {
        this(commitModel.getCommitId(), commitModel.getVersion());
    }

    public VersionCommitModel(CommitVersion commitVersion) {
        this(null, commitVersion);
    }
}
