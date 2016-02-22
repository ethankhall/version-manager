package io.ehdev.conrad.service.api.service.model;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.service.api.service.ProjectEndpoint;
import io.ehdev.conrad.service.api.service.RepoEndpoint;
import io.ehdev.conrad.service.api.service.RepoVersionEndpoint;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class LinkUtilities {

    public static final String VERSION_REF = "versions";
    public static final String REPOSITORY_REF = "repository";

    private LinkUtilities() { }

    public static Link repositorySelfLink(ApiParameterContainer container) {
        return repositorySelfLink(container, container.getRepoName());
    }

    public static Link repositorySelfLink(ApiParameterContainer container, String repoName) {
        return linkTo(methodOn(RepoEndpoint.class, container.getProjectName(), repoName)
            .getRepoDetails(container)).withRel("self");
    }

    public static Link repositoryLink(ApiParameterContainer container, String linkName) {
        return linkTo(methodOn(RepoEndpoint.class, container.getProjectName(), container.getRepoName())
            .getRepoDetails(container)).withRel(linkName);
    }

    public static Link versionListLink(ApiParameterContainer container, String linkName) {
        return linkTo(methodOn(RepoVersionEndpoint.class, container.getProjectName(), container.getRepoName())
            .getAllVersions(container)).withRel(linkName);
    }

    public static Link versionSelfLink(ApiParameterContainer container, String version) {
        return linkTo(methodOn(RepoVersionEndpoint.class, container.getProjectName(), container.getRepoName())
            .findVersion(container, version)).withSelfRel();
    }

    public static Link projectSelfLink(ApiParameterContainer apiParameterContainer) {
        return linkTo(methodOn(ProjectEndpoint.class, apiParameterContainer.getProjectName()).getProject(apiParameterContainer)).withSelfRel();
    }

    public static Link projectLink(ApiParameterContainer apiParameterContainer, String name) {
        return linkTo(methodOn(ProjectEndpoint.class, apiParameterContainer.getProjectName()).getProject(apiParameterContainer)).withSelfRel();
    }
}
