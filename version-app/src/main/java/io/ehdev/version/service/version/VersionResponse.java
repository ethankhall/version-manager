package io.ehdev.version.service.version;

import com.fasterxml.jackson.annotation.JsonView;
import io.ehdev.version.model.commit.CommitVersion;

import java.util.List;

public class VersionResponse {

    @JsonView(VersionView.ReleasedVersionView.class)
    final String commit;

    @JsonView(VersionView.UnreleasedVersionView.class)
    final String version;

    @JsonView(VersionView.UnreleasedVersionView.class)
    final List<String> group;

    public VersionResponse(String commit, CommitVersion commitVersion) {
        this.commit = commit;
        this.version = commitVersion.toVersionString();
        this.group = commitVersion.getGroup();
    }
}
