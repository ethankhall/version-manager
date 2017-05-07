package io.ehdev.conrad.gradle;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.testing.Test;
import org.gradle.plugins.ide.idea.model.IdeaModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class AddTestSourceSets {
    public static void addSourceSet(final Project project, final String name) {
        JavaPluginConvention javaPlugin = project.getConvention().getPlugin(JavaPluginConvention.class);
        SourceSetContainer sourceSets = javaPlugin.getSourceSets();

        final SourceSet mainSourceSet = sourceSets.getByName("main");
        final SourceSet testSourceSet = sourceSets.getByName("test");

        SourceSet newSourceSet = sourceSets.create(name, sourceSet -> {
            FileCollection testClasspath = mainSourceSet.getOutput().plus(testSourceSet.getOutput());
            sourceSet.setCompileClasspath(sourceSet.getCompileClasspath().plus(testClasspath));
            sourceSet.setRuntimeClasspath(sourceSet.getRuntimeClasspath().plus(testClasspath));
            sourceSet.getJava().srcDir(project.file("src/" + name + "/java"));
            sourceSet.getResources().srcDir(project.file("src/" + name + "/resources"));
        });

        Configuration compileConfig = project.getConfigurations().getByName(name + "Compile");
        Configuration runtimeConfig = project.getConfigurations().getByName(name + "Runtime");

        compileConfig.extendsFrom(project.getConfigurations().getByName("testCompile"));
        runtimeConfig.extendsFrom(project.getConfigurations().getByName("testRuntime"));


        Test integTest = project.getTasks().create(name, Test.class, test -> {
            test.shouldRunAfter(project.getTasks().getByName("test"));
            test.setTestClassesDir(newSourceSet.getOutput().getClassesDir());
            test.setClasspath(newSourceSet.getRuntimeClasspath());
            test.getReports().getHtml().setDestination(new File(project.getBuildDir(), "/reports/" + name));
        });

        project.getTasks().getByName("check").dependsOn(integTest);

        IdeaModel idea = project.getExtensions().getByType(IdeaModel.class);

        File sourceSetDir = new File(project.getProjectDir(), "src/" + name);

        List<File> sourceDirs = new ArrayList<>();
        sourceDirs.addAll(Arrays.asList(new File(sourceSetDir, "java"), new File(sourceSetDir, "groovy")));
        sourceDirs.addAll(idea.getModule().getTestSourceDirs());
        sourceDirs.addAll(newSourceSet.getResources().getSrcDirs());

        idea.getModule().setTestSourceDirs(new HashSet<>(sourceDirs));

        idea.getModule().getScopes().get("TEST").get("plus").add(compileConfig);
        idea.getModule().getScopes().get("TEST").get("plus").add(runtimeConfig);
    }
}
