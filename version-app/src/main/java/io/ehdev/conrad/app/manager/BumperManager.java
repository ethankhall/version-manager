package io.ehdev.conrad.app.manager;

import io.ehdev.conrad.app.exception.UnknownBumperException;
import io.ehdev.conrad.backend.version.bumper.VersionBumper;
import io.ehdev.conrad.database.model.VcsRepoModel;
import io.ehdev.conrad.database.model.VersionBumperModel;
import io.ehdev.conrad.database.repository.VcsRepoRepository;
import io.ehdev.conrad.database.repository.VersionBumperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.*;

@Service
public class BumperManager {

    private static final Logger log = LoggerFactory.getLogger(BumperManager.class);

    private final VersionBumperRepository versionBumperRepository;
    private final VcsRepoRepository vcsRepoRepository;
    private final Map<String, VersionBumper> versionBumpers = new HashMap<>();

    @Autowired
    public BumperManager(final Set<VersionBumper> bumperSet,
                         final VcsRepoRepository vcsRepoRepository,
                         final VersionBumperRepository versionBumperRepository) {
        bumperSet.forEach(bumper -> versionBumpers.put(bumper.getClass().getName(), bumper));
        this.vcsRepoRepository = vcsRepoRepository;
        this.versionBumperRepository = versionBumperRepository;
    }

    @Nonnull
    public VersionBumper findVersionBumper(UUID repoId) {
        VcsRepoModel repo = vcsRepoRepository.findByUuid(repoId);
        VersionBumper versionBumper = versionBumpers.get(repo.getVersionBumperName());
        log.debug("Found {} for repo {}", versionBumper, repoId.toString());

        if (null == versionBumper) {
            throw new UnknownBumperException();
        } else {
            return versionBumper;
        }
    }

    public List<VersionBumperModel> findAllVersionBumpers() {
        return versionBumperRepository.findAll();
    }
}
