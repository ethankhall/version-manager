package io.ehdev.conrad.client.java;

import java.util.concurrent.TimeUnit;

public class VersionServiceConfiguration {
    String repoId;
    String providerBaseUrl;
    int timeout = (int)TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS);

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getProviderBaseUrl() {
        return providerBaseUrl;
    }

    public void setProviderBaseUrl(String providerBaseUrl) {
        this.providerBaseUrl = providerBaseUrl;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timout) {
        this.timeout = timout;
    }
}
