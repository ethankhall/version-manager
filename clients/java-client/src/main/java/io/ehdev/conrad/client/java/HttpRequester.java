package io.ehdev.conrad.client.java;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpRequester {

    HttpClient client;
    VersionServiceConfiguration extension;

    public HttpRequester(VersionServiceConfiguration extension) {
        this.client = new DefaultHttpClient();
        this.extension = extension;
    }

//    public Version getVersion() throws URISyntaxException {
//        URIBuilder uriBuilder = new URIBuilder(extension.getProviderBaseUrl() + "/api/version");
//        uriBuilder.setParameter("repoId", extension.getRepoId());
//        uriBuilder.setParameter("parentCommit", "");
//        return null;
//    }
}
