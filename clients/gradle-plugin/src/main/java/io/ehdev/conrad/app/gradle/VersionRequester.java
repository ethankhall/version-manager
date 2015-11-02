package io.ehdev.conrad.app.gradle;

import io.ehdev.conrad.client.java.exception.UnsuccessfulRequestException;
import io.ehdev.conrad.client.java.internal.DefaultVersionManagerClient;
import io.ehdev.conrad.client.java.VersionManagerClient;
import io.ehdev.conrad.client.java.Version;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.util.GFileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class VersionRequester {

    private static final Logger logger = Logging.getLogger(VersionRequester.class);

    private final VersionManagerExtension extension;
    private final File rootProjectDir;
    private final File backupFile;
    private final boolean onlyFinalVersion;
    private Version version;

    public VersionRequester(VersionManagerExtension extension, File rootProjectDir, boolean findFinalVersion) {
        this.extension = extension;
        this.rootProjectDir = rootProjectDir;
        this.backupFile = new File(rootProjectDir, ".gradle/version-manager.txt");
        this.onlyFinalVersion = findFinalVersion;
    }

    void evaluate() {
        try {
            VersionManagerClient versionManagerClient = new DefaultVersionManagerClient(extension.getClient(), extension);
            if(onlyFinalVersion) {
                version = versionManagerClient.findFinalVersion(rootProjectDir);
            } else {
                version = versionManagerClient.findVersion(rootProjectDir);
            }
            persistBackup();
        } catch (IOException | GitAPIException | URISyntaxException | UnsuccessfulRequestException e) {
            String versionString = getBackup();
            if(null == versionString) {
                logger.error("Unable to get version ({}), using default version.", e.getMessage());
                versionString = "0.0.1-SNAPSHOT";
            } else {
                logger.warn("Unable to get version ({}), using last version.", e.getMessage());
            }
            version = new Version(versionString);
        }
    }

    private void persistBackup() {
        GFileUtils.writeFile(version.getVersion(), backupFile);
    }

    private String getBackup() {
        if(!backupFile.exists()) {
            return null;
        }

        try {
            String version = GFileUtils.readFile(backupFile);
            return StringUtils.trimToNull(version);
        } catch(UncheckedIOException ignore) {
            return null;
        }
    }

    @Override
    public String toString() {
        if(null == version) {
            evaluate();
        }
        return version.getVersion();
    }
}
