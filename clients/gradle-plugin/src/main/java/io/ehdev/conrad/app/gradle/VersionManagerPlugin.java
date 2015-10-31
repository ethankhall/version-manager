package io.ehdev.conrad.app.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class VersionManagerPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        VersionManagerExtension versionManager = project.getExtensions().create("versionManager", VersionManagerExtension.class);
        VersionRequester versionRequester = new VersionRequester(versionManager, project.getProjectDir());
        project.setVersion(versionRequester);
    }
}
