package io.ehdev.conrad.app.gradle;

import io.ehdev.conrad.client.java.Version;
import io.ehdev.conrad.client.java.VersionManagerClient;
import io.ehdev.conrad.client.java.exception.UnsuccessfulRequestException;
import io.ehdev.conrad.client.java.internal.DefaultVersionManagerClient;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.io.File;
import java.io.IOException;

public class VersionClaimer {

    private static final Logger logger = Logging.getLogger(VersionClaimer.class);

    private final VersionManagerExtension extension;
    private final File rootProjectDir;

    public VersionClaimer(VersionManagerExtension extension, File rootProjectDir) {
        this.extension = extension;
        this.rootProjectDir = rootProjectDir;
    }

    Version claimVersion() {
        try {
            VersionManagerClient versionManagerClient = new DefaultVersionManagerClient(extension.getClient(), extension);
            Version version = versionManagerClient.claimVersion(rootProjectDir);
            logger.lifecycle("Version claimed was: {}", version.getVersion());
            return version;
        } catch (IOException | GitAPIException | UnsuccessfulRequestException e) {
            logger.error("Unable to claim version! Reason: {}", e.getMessage());
            logger.info("Unable to claim version! Reason: {}", e.getMessage(), e);
            throw new UnableToClaimVersionException(e);
        }
    }

    public static class UnableToClaimVersionException extends RuntimeException {
        public UnableToClaimVersionException(Exception e) {
            super(e);
        }
    }
}
