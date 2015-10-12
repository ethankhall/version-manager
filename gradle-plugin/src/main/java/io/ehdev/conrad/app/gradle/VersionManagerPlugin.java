package io.ehdev.conrad.app.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class VersionManagerPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getExtensions().create("versionManager", VersionManagerExtension.class);

        project.afterEvaluate(evaluated -> { evaluated.setVersion(new Version(""));

        });
    }
}
