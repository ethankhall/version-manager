package io.ehdev.conrad.test.database.repository
import io.ehdev.conrad.backend.version.bumper.SemanticVersionBumper
import io.ehdev.conrad.backend.version.commit.CommitVersion
import io.ehdev.conrad.database.internal.CommitModel
import io.ehdev.conrad.database.internal.VcsRepoModel
import io.ehdev.conrad.database.internal.VersionBumperModel
import io.ehdev.conrad.database.repository.CommitModelRepository
import io.ehdev.conrad.database.repository.VcsRepoRepository
import io.ehdev.conrad.database.repository.VersionBumperRepository
import org.apache.commons.lang3.RandomStringUtils

public class PopulateTestUtils {

    public static VersionBumperModel createBumper(VersionBumperRepository repository) {
        VersionBumperModel bumper = repository.findByBumperName("semver");
        if(bumper == null) {
            def model = new VersionBumperModel(SemanticVersionBumper.class.getName(),
                SemanticVersionBumper.class.getSimpleName(),
                "semver")
            model.setId(UUID.randomUUID())
            return repository.save(model);
        } else {
            return bumper;
        }
    }

    public static VcsRepoModel repo(VcsRepoRepository vcsRepoRepository, VersionBumperRepository repository) {
        def model = new VcsRepoModel(RandomStringUtils.randomAlphabetic(11), createBumper(repository))
        model.setId(UUID.randomUUID())
        return vcsRepoRepository.save(model);
    }

    public static VersionBumperModel stubBumper() {
        def model = new VersionBumperModel(SemanticVersionBumper.class.getName(),
            SemanticVersionBumper.class.getSimpleName(),
            SemanticVersionBumper.class.getSimpleName())
        model.setId(UUID.randomUUID())
        return model;
    }

    public static VcsRepoModel stubRepo(VersionBumperModel bumper, String name = RandomStringUtils.randomAlphabetic(11)) {
        def model = new VcsRepoModel(name, bumper)
        model.setId(UUID.randomUUID())
        return model;
    }

    public static CommitModel createCommit(CommitModelRepository commitModelRepository, VcsRepoModel vcsRepoModel,
                                           CommitVersion commitVersion, CommitModel parent) {
        def model = new CommitModel(RandomStringUtils.randomAlphanumeric(40), vcsRepoModel, commitVersion, parent)
        model.setId(UUID.randomUUID())
        return commitModelRepository.save(model);
    }
}
