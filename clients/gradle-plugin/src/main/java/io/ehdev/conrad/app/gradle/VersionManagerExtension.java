package io.ehdev.conrad.app.gradle;

import io.ehdev.conrad.client.java.VersionServiceConfiguration;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class VersionManagerExtension extends VersionServiceConfiguration {
    private HttpClient client;

    public VersionManagerExtension() {
        client = new DefaultHttpClient();
    }

    public HttpClient getClient() {
        return client;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }
}
