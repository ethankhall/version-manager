package io.ehdev.conrad.service.api.service.advice.link;

import io.ehdev.conrad.model.DefaultResourceSupport;
import io.ehdev.conrad.model.ResourceLink;
import io.ehdev.conrad.service.api.service.annotation.InternalLink;
import io.ehdev.conrad.service.api.service.annotation.InternalLinks;
import org.apache.commons.io.FilenameUtils;
import tech.crom.model.security.authorization.CromPermission;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public class DefaultLinkControllerAdviceHelper implements LinkControllerAdviceHelper {

    private final DefaultResourceSupport resourceSupport;
    private final CromPermission permission;
    private final String fullUrl;

    public DefaultLinkControllerAdviceHelper(DefaultResourceSupport resourceSupport, CromPermission permission, String fullUrl) {
        this.resourceSupport = resourceSupport;
        this.permission = permission;
        this.fullUrl = fullUrl;
    }

    @Override
    public void addLinks(InternalLinks annotation) {
        resourceSupport.addLink(new ResourceLink("self", fullUrl));
        for (InternalLink internalLink : annotation.links()) {
            if (permission.isHigherOrEqualThan(internalLink.permissions())) {
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
        String requestUrlString = request.getRequestURL().toString();
        if(requestUrlString.endsWith(".xml") || requestUrlString.endsWith(".json")) {
            requestUrlString = FilenameUtils.removeExtension(requestUrlString);
        }
        StringBuilder requestUrl = new StringBuilder(requestUrlString);
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestUrl.toString();
        } else {
            return requestUrl.append('?').append(queryString).toString();
        }
    }
}
