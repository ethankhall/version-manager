package io.ehdev.conrad.app.gradle;

import io.ehdev.conrad.client.java.HttpRequester;
import io.ehdev.conrad.client.java.Version;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class VersionRequester {

    private static final Logger logger = Logging.getLogger(VersionRequester.class);

    private final VersionManagerExtension extension;
    private final File projectDir;
    private Version version;

    public VersionRequester(VersionManagerExtension extension, File projectDir) {
        this.extension = extension;
        this.projectDir = projectDir;
    }

    void evaluate() {
        try {
            HttpRequester httpRequester = new HttpRequester(extension.getClient(), extension);
            version = httpRequester.getVersion(projectDir);
        } catch (IOException | GitAPIException | URISyntaxException|HttpRequester.UnsuccessfulRequestException e) {
            logger.error("Unable to get version ({}), using default value.", e.getMessage());
            version = new Version("0.0.1-SNAPSHOT");
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
