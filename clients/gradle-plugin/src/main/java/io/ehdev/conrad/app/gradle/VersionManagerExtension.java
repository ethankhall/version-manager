package io.ehdev.conrad.app.gradle;

import io.ehdev.conrad.client.java.VersionServiceConfiguration;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class VersionManagerExtension extends VersionServiceConfiguration {
    private HttpClient client;
    final HttpParams httpParams = new BasicHttpParams();

    public VersionManagerExtension() {
        HttpConnectionParams.setConnectionTimeout(httpParams, this.getTimeout());
        client = new DefaultHttpClient();
    }

    public HttpClient getClient() {
        return client;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }

    @Override
    public void setTimeout(int timeout) {
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
        super.setTimeout(timeout);
    }
}
