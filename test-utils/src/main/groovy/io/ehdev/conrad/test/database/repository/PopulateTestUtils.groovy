package io.ehdev.conrad.test.database.repository
import io.ehdev.conrad.backend.version.bumper.SemanticVersionBumper
import io.ehdev.conrad.backend.version.commit.CommitVersion
import io.ehdev.conrad.database.model.CommitModel
import io.ehdev.conrad.database.model.VcsRepoModel
import io.ehdev.conrad.database.model.VersionBumperModel
import io.ehdev.conrad.database.repository.CommitModelRepository
import io.ehdev.conrad.database.repository.VcsRepoRepository
import io.ehdev.conrad.database.repository.VersionBumperRepository
import org.apache.commons.lang3.RandomStringUtils

public class PopulateTestUtils {

    public static VersionBumperModel createBumper(VersionBumperRepository repository) {
        VersionBumperModel bumper = repository.findByBumperName('semver');
        if(bumper == null) {
            return repository.save(new VersionBumperModel(SemanticVersionBumper.class.getName(),
                SemanticVersionBumper.class.getSimpleName(),
                SemanticVersionBumper.class.getSimpleName()));
        } else {
            return bumper;
        }
    }

    public static VcsRepoModel repo(VcsRepoRepository vcsRepoRepository, VersionBumperRepository repository) {
        def model = new VcsRepoModel(UUID.randomUUID(), RandomStringUtils.randomAlphabetic(11), createBumper(repository))
        return vcsRepoRepository.save(model);
    }

    public static VersionBumperModel stubBumper() {
        return new VersionBumperModel(SemanticVersionBumper.class.getName(),
            SemanticVersionBumper.class.getSimpleName(),
            SemanticVersionBumper.class.getSimpleName());
    }

    public static VcsRepoModel stubRepo(VersionBumperModel bumper, String name = RandomStringUtils.randomAlphabetic(11)) {
        return new VcsRepoModel(UUID.randomUUID(), name, bumper);
    }

    public static CommitModel createCommit(CommitModelRepository commitModelRepository, VcsRepoModel vcsRepoModel,
                                           CommitVersion commitVersion, CommitModel parent) {
        def model = new CommitModel(RandomStringUtils.randomAlphanumeric(40), vcsRepoModel, commitVersion, parent)
        return commitModelRepository.save(model);
    }
}
