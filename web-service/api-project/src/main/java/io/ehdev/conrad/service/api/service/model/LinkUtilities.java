package io.ehdev.conrad.service.api.service.model;

import io.ehdev.conrad.database.model.repo.RequestDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.model.ResourceLink;
import io.ehdev.conrad.service.api.service.repo.RepoEndpoint;
import io.ehdev.conrad.service.api.service.repo.RepoVersionEndpoint;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class LinkUtilities {

    public static final String VERSION_REF = "versions";

    private LinkUtilities() { }

    public static Link repositorySelfLink(RequestDetails container, String repoName) {
        return linkTo(methodOn(RepoEndpoint.class, getResource(container).getProjectId().getName(), repoName)
            .getRepoDetails(container)).withRel("self");
    }

    public static Link repositoryLink(RequestDetails container, String linkName) {
        return linkTo(methodOn(RepoEndpoint.class, getResource(container).getProjectId().getName(), getResource(container).getRepoId().getName())
            .getRepoDetails(container)).withRel(linkName);
    }

    private static ResourceDetails getResource(RequestDetails container) {
        return container.getResourceDetails();
    }

    public static Link versionSelfLink(RequestDetails container, String version) {
        return linkTo(methodOn(RepoVersionEndpoint.class, getResource(container).getProjectId().getName(), getResource(container).getRepoId().getName())
            .findVersion(container, version)).withSelfRel();
    }

    public static ResourceLink toLink(Link link) {
        return new ResourceLink(link.getRel(), link.getHref());
    }
}
