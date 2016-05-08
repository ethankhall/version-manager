package io.ehdev.conrad.service.api.service.advice.link;

import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.model.DefaultResourceSupport;
import io.ehdev.conrad.model.ResourceLink;
import io.ehdev.conrad.service.api.service.annotation.InternalLink;
import io.ehdev.conrad.service.api.service.annotation.InternalLinks;

import javax.servlet.http.HttpServletRequest;

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
                resourceSupport.addLink(new ResourceLink(internalLink.name(), fullUrl + internalLink.ref()));
            }
        }
    }

    public static String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
