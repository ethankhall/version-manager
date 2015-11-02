package io.ehdev.conrad.app.gradle;

import org.apache.commons.lang.StringUtils;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class VersionManagerPlugin implements Plugin<Project> {

    public static final String FINAL_VERSION_PROPERTY = "finalVersion";

    @Override
    public void apply(Project project) {
        boolean isFinalVersion = ifFinalVersionBuild(project);

        VersionManagerExtension versionManager = project.getExtensions().create("versionManager", VersionManagerExtension.class);
        VersionRequester versionRequester = new VersionRequester(versionManager, project.getProjectDir(), isFinalVersion);
        project.setVersion(versionRequester);

        ClaimVersionTask claimVersion = project.getTasks().create("claimVersion", ClaimVersionTask.class);
        claimVersion.setDescription("Claims a version from the version-manager service.");
        claimVersion.setGroup("Version Manager");
    }

    boolean ifFinalVersionBuild(Project project) {
        return project.hasProperty(FINAL_VERSION_PROPERTY)
            && StringUtils.equalsIgnoreCase("true", project.property(FINAL_VERSION_PROPERTY).toString());
    }
}
