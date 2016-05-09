package io.ehdev.conrad.service.api.service.advice.link;

import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.model.DefaultResourceSupport;
import io.ehdev.conrad.model.ResourceLink;
import io.ehdev.conrad.service.api.service.annotation.InternalLink;
import io.ehdev.conrad.service.api.service.annotation.InternalLinks;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public class DefaultLinkControllerAdviceHelper implements LinkControllerAdviceHelper {

    private final DefaultResourceSupport resourceSupport;
    private final ApiUserPermission permission;
    private final String fullUrl;

    public DefaultLinkControllerAdviceHelper(DefaultResourceSupport resourceSupport, ApiUserPermission permission, String fullUrl) {
        this.resourceSupport = resourceSupport;
        this.permission = permission;
        this.fullUrl = fullUrl;
    }

    @Override
    public void addLinks(InternalLinks annotation) {
        resourceSupport.addLink(new ResourceLink("self", fullUrl));
        for (InternalLink internalLink : annotation.links()) {
            if (permission.isHigherOrEqualTo(internalLink.permissions())) {
                addLink(internalLink);
            }
        }
    }

    private void addLink(InternalLink internalLink) {
        String href = URI.create(fullUrl + internalLink.ref()).normalize().toString();
        if(href.endsWith("/")) {
            href = href.substring(0, href.length() - 1);
        }
        ResourceLink resourceLink = new ResourceLink(internalLink.name(), href);
        resourceSupport.addLink(resourceLink);
    }

    public static String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(FilenameUtils.removeExtension(request.getRequestURL().toString()));
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}