package io.ehdev.conrad.app.gradle.requester;

import io.ehdev.conrad.app.gradle.Version;
import io.ehdev.conrad.app.gradle.VersionManagerExtension;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URISyntaxException;

public class HttpRequester {

    HttpClient client;
    VersionManagerExtension extension;

    public HttpRequester(VersionManagerExtension extension) {
        this.client = new DefaultHttpClient();
        this.extension = extension;
    }

    public Version getVersion() throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(extension.getProviderBaseUrl() + "/api/version");
        uriBuilder.setParameter("repoId", extension.getRepoId());
        uriBuilder.setParameter("parentCommit", "");
        return null;
    }
}
