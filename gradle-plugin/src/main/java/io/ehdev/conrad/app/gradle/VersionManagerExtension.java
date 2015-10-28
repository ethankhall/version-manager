package io.ehdev.conrad.app.gradle;

public class VersionManagerExtension {
    String repoId;
    String providerBaseUrl;
    String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
