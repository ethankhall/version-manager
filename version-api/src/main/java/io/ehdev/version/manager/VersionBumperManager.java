package io.ehdev.version.manager;

import io.ehdev.version.commit.model.RepositoryCommitModel;
import io.ehdev.version.update.VersionBumper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class VersionBumperManager {

    private static final Logger log = LoggerFactory.getLogger(VersionBumperManager.class);

    final Map<String, VersionBumper> versionBumpers = new HashMap<>();

    @Autowired
    public VersionBumperManager(final Set<VersionBumper> bumperSet) {
        bumperSet.forEach(bumper -> versionBumpers.put(bumper.name(), bumper));
    }

    public VersionBumper findVersionBumper(RepositoryCommitModel commit) {
        String versionBumperName = commit.getScmMetaDataModel().getVersionBumperName();

        log.debug("[{}] Version Bumper Name: {}", commit.getCommitId(), versionBumperName);

        return versionBumpers.get(versionBumperName);
    }
}
