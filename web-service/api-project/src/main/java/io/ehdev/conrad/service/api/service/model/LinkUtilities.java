package io.ehdev.conrad.service.api.service.model;

import io.ehdev.conrad.model.ResourceLink;
import io.ehdev.conrad.service.api.service.repo.RepoEndpoint;
import io.ehdev.conrad.service.api.service.repo.RepoVersionEndpoint;
import org.springframework.hateoas.Link;
import tech.crom.web.api.model.RequestDetails;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class LinkUtilities {

    public static final String VERSION_REF = "versions";

    private LinkUtilities() { }

    public static Link repositorySelfLink(RequestDetails container, String repoName) {
        return linkTo(methodOn(RepoEndpoint.class, container.getRawRequest().getProjectName(), repoName)
            .getRepoDetails(container)).withRel("self");
    }

    public static Link repositoryLink(RequestDetails container, String linkName) {
        return linkTo(methodOn(RepoEndpoint.class, container.getRawRequest().getProjectName(), container.getRawRequest().getRepoName())
            .getRepoDetails(container)).withRel(linkName);
    }

    public static Link versionSelfLink(RequestDetails container, String version) {
        return linkTo(methodOn(RepoVersionEndpoint.class, container.getRawRequest().getProjectName(), container.getRawRequest().getRepoName())
            .findVersion(container, version)).withSelfRel();
    }

    public static ResourceLink toLink(Link link) {
        return new ResourceLink(link.getRel(), link.getHref());
    }
}
