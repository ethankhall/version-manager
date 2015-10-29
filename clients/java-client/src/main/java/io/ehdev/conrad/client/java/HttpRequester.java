package io.ehdev.conrad.client.java;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ehdev.conrad.model.version.UncommitedVersionModel;
import io.ehdev.conrad.model.version.VersionSearchModel;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class HttpRequester {

    private static final ObjectMapper om = new ObjectMapper();

    final Executor executor;
    final VersionServiceConfiguration configuration;

    public HttpRequester(VersionServiceConfiguration configuration) {
        this(Executor.newInstance(), configuration);
    }

    public HttpRequester(Executor executor, VersionServiceConfiguration configuration) {
        this.executor = executor;
        this.configuration = configuration;
    }

    public Version getVersion(List<String> commitIds) throws URISyntaxException, IOException {
        String body = om.writeValueAsString(new VersionSearchModel(commitIds));
        Request getRequest = Request.Post(createSearchUrl())
            .bodyString(body, ContentType.APPLICATION_JSON)
            .connectTimeout(configuration.getTimout())
            .socketTimeout(configuration.getTimout());

        String content = executor.execute(getRequest).returnContent().asString();

        UncommitedVersionModel repoVersionModel = om.readValue(content, UncommitedVersionModel.class);
        return new Version(repoVersionModel.getVersion());
    }

    @NotNull
    private String createSearchUrl() {
        return configuration.getProviderBaseUrl() + "/api/version/" + configuration.getRepoId() + "/search";
    }
}
