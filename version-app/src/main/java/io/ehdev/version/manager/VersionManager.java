package io.ehdev.version.manager;

import io.ehdev.version.model.CommitVersion;
import io.ehdev.version.service.version.VersionSearchModel;
import org.springframework.stereotype.Service;

@Service
public class VersionManager {

    public CommitVersion findVersion(String repoId, VersionSearchModel versionSearchModel) {
        return null;
    }
}
