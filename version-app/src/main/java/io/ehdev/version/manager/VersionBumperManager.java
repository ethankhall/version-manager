package io.ehdev.version.manager;

import io.ehdev.version.manager.exception.UnknownBumperException;
import io.ehdev.version.model.commit.model.RepositoryCommitModel;
import io.ehdev.version.model.commit.model.VersionBumperModel;
import io.ehdev.version.model.repository.VersionBumperRepository;
import io.ehdev.version.model.update.VersionBumper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class VersionBumperManager {

    private static final Logger log = LoggerFactory.getLogger(VersionBumperManager.class);

    private final VersionBumperRepository versionBumperRepository;
    private final Map<String, VersionBumper> versionBumpers = new HashMap<>();

    @Autowired
    public VersionBumperManager(final Set<VersionBumper> bumperSet, final VersionBumperRepository versionBumperRepository) {
        this.versionBumperRepository = versionBumperRepository;
        bumperSet.forEach(bumper -> versionBumpers.put(bumper.getClass().getName(), bumper));
    }

    @Nonnull
    public VersionBumper findVersionBumper(RepositoryCommitModel commit) {
        String versionBumperName = commit.getScmMetaDataModel().getVersionBumperName();

        log.debug("[{}] Version Bumper Name: {}", commit.getCommitId(), versionBumperName);
        VersionBumperModel bumper = versionBumperRepository.findByBumperName(versionBumperName);

        if (null == bumper) {
            throw new UnknownBumperException();
        }

        return versionBumpers.get(bumper.getClassName());
    }
}
