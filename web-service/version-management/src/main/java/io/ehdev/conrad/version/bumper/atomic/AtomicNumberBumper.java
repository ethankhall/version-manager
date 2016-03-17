package io.ehdev.conrad.version.bumper.atomic;

import io.ehdev.conrad.version.bumper.VersionBumper;
import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.details.CommitDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class AtomicNumberBumper implements VersionBumper {

    private static final Logger log = LoggerFactory.getLogger(AtomicNumberBumper.class);

    @Override
    public CommitVersion createNextVersion(String parentVersion, CommitDetails commitDetails) {
        CommitVersion nextVersion = createDefaultNextVersion(parentVersion);
        log.info("Next version for commit {} is {}", commitDetails.getCommitId(), nextVersion.toVersionString());
        return nextVersion;
    }

    @Override
    public CommitVersion createDefaultNextVersion(String parentVersion) {
        return new AtomicCommitVersion(AtomicCommitVersion.parse(parentVersion).getVersion() + 1);
    }
}
