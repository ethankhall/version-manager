package io.ehdev.conrad.app.gradle;

import io.ehdev.conrad.client.java.Version;
import io.ehdev.conrad.client.java.VersionManagerClient;
import io.ehdev.conrad.client.java.exception.UnsuccessfulRequestException;
import io.ehdev.conrad.client.java.internal.DefaultVersionManagerClient;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.gradle.api.Project;
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
    private boolean onlyFinalVersion;
    private Version version;

    public VersionRequester(VersionManagerExtension extension, boolean finalVersion, Project rootProject) {
        this.extension = extension;
        this.rootProjectDir = rootProject.getProjectDir();
        this.backupFile = new File(rootProject.getProjectDir(), ".gradle/version-manager.txt");
        this.onlyFinalVersion = finalVersion;

        for (String taskName : rootProject.getGradle().getStartParameter().getTaskNames()) {
            if(taskName.matches("(:?)claimVersion$")) {
                onlyFinalVersion = true;
                break;
            }
        }


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
