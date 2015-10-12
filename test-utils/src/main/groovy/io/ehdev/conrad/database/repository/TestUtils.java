package io.ehdev.conrad.database.repository;

import io.ehdev.conrad.backend.version.bumper.SemanticVersionBumper;
import io.ehdev.conrad.backend.version.commit.CommitVersion;
import io.ehdev.conrad.database.model.CommitModel;
import io.ehdev.conrad.database.model.VcsRepoModel;
import io.ehdev.conrad.database.model.VersionBumperModel;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class TestUtils {

    public static VersionBumperModel createBumper(VersionBumperRepository repository) {
        VersionBumperModel bumper = repository.findByBumperName(SemanticVersionBumper.class.getSimpleName());
        if(bumper == null) {
            return repository.save(new VersionBumperModel(SemanticVersionBumper.class.getName(),
                SemanticVersionBumper.class.getSimpleName(),
                SemanticVersionBumper.class.getSimpleName()));
        } else {
            return bumper;
        }
    }

    public static VcsRepoModel repo(VcsRepoRepository vcsRepoRepository, VersionBumperRepository repository) {
        return vcsRepoRepository.save(new VcsRepoModel(UUID.randomUUID(), RandomStringUtils.randomAlphabetic(11), createBumper(repository)));
    }

    public static CommitModel createCommit(CommitModelRepository commitModelRepository, VcsRepoModel vcsRepoModel,
                                           CommitVersion commitVersion, CommitModel parent) {
        return commitModelRepository.save(new CommitModel(RandomStringUtils.randomAlphanumeric(40), vcsRepoModel, commitVersion, parent));
    }
}
