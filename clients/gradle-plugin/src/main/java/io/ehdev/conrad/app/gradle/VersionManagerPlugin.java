package io.ehdev.conrad.app.gradle;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class VersionManagerPlugin implements Plugin<Project> {

    public static final String FINAL_VERSION_PROPERTY = "finalVersion";

    @Override
    public void apply(Project project) {
        boolean isFinalVersion = ifFinalVersionBuild(project);

        VersionManagerExtension versionManager = project.getExtensions().create("versionManager", VersionManagerExtension.class);
        final VersionRequester versionRequester = new VersionRequester(versionManager, ifFinalVersionBuild(project), project);
        project.setVersion(versionRequester);
        project.allprojects(new Action<Project>() {
            @Override
            public void execute(Project project) {
                project.setVersion(versionRequester);
            }
        });

        ClaimVersionTask claimVersion = project.getTasks().create("claimVersion", ClaimVersionTask.class);
        claimVersion.setDescription("Claims a version from the version-manager service.");
        claimVersion.setGroup("Version Manager");
    }

    boolean ifFinalVersionBuild(Project project) {
        return project.hasProperty(FINAL_VERSION_PROPERTY)
            && "true".equalsIgnoreCase(project.property(FINAL_VERSION_PROPERTY).toString());
    }
}
