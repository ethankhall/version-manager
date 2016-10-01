package io.ehdev.conrad.service.api.service.model;

import io.ehdev.conrad.model.ResourceLink;
import tech.crom.web.api.model.RequestDetails;

public class LinkUtilities {

    public static final String VERSION_REF = "versions";

    private LinkUtilities() { }

    public static ResourceLink repositorySelfLink(RequestDetails container) {
        return new ResourceLink("self", container.getRawRequest().getRequestPath());
    }

    public static ResourceLink versionSelfLink(RequestDetails container, String version) {
        String url = "/api/v1/project/" +
            container.getCromProject().getProjectName() +
            "/repo/" + container.getCromRepo().getRepoName() +
            "/version/" + version;

        return new ResourceLink("self", url);
    }
}
