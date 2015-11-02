package io.ehdev.conrad.app.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public class ClaimVersionTask extends DefaultTask {

    @Input
    public VersionManagerExtension getVersionExtension() {
        return getProject().getExtensions().getByType(VersionManagerExtension.class);
    }

    @TaskAction
    public void claimVersion() {
        VersionManagerExtension versionExtension = getVersionExtension();
        VersionClaimer versionClaimer = new VersionClaimer(versionExtension, getProject().getRootDir());
        versionClaimer.claimVersion();
    }
}
