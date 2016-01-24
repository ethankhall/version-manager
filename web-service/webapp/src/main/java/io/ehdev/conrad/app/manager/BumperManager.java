package io.ehdev.conrad.app.manager;

import io.ehdev.conrad.app.exception.UnknownBumperException;
import io.ehdev.conrad.backend.version.bumper.VersionBumper;
import io.ehdev.conrad.database.impl.VcsRepoModel;
import io.ehdev.conrad.database.impl.VersionBumperModel;
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

        versionBumperRepository.findAll().forEach(bumper -> {
            if (!versionBumpers.containsKey(bumper.getClassName())) {
                throw new RuntimeException("Unable to find bumper " + bumper.getClassName());
            }
        });
    }

    @Nonnull
    public VersionBumper findVersionBumper(UUID repoId) {
        VcsRepoModel repo = vcsRepoRepository.findOne(repoId);
        VersionBumper versionBumper = versionBumpers.get(repo.getVersionBumpterClassName());
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

    public VersionBumperModel findByName(String bumperName) {
        return versionBumperRepository.findByBumperName(bumperName);
    }
}
