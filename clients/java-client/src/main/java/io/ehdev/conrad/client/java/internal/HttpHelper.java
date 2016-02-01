package io.ehdev.conrad.client.java.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ehdev.conrad.client.java.VersionServiceConfiguration;
import io.ehdev.conrad.client.java.exception.UnsuccessfulRequestException;
import io.ehdev.conrad.model.repo.RepoCreateModel;
import io.ehdev.conrad.model.repo.RepoResponseModel;
import io.ehdev.conrad.model.rest.RestCommitModel;
import io.ehdev.conrad.model.version.VersionCommitModel;
import io.ehdev.conrad.model.version.VersionCreateModel;
import io.ehdev.conrad.model.version.VersionSearchModel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class HttpHelper {

    private static final Logger logger = LoggerFactory.getLogger(HttpHelper.class);
    private static final ObjectMapper om = new ObjectMapper();

    private final HttpClient client;
    private final VersionServiceConfiguration configuration;

    public HttpHelper(HttpClient client, VersionServiceConfiguration configuration) {
        this.client = client;
        this.configuration = configuration;
    }

    public RestCommitModel findVersion(VersionSearchModel searchModel) throws IOException, UnsuccessfulRequestException {
        String body = om.writeValueAsString(searchModel);
        InputStream content = doPostRequest(body, createVersionSearchUrl()).getEntity().getContent();
        return om.readValue(content, RestCommitModel.class);
    }

    public VersionCommitModel findFinalVersion(String commitId) throws IOException, UnsuccessfulRequestException {
        InputStream content = doGetRequest(createVerionFindUrl(commitId)).getEntity().getContent();
        return om.readValue(content, VersionCommitModel.class);
    }

    public RestCommitModel claimVersion(VersionCreateModel createModel) throws IOException, UnsuccessfulRequestException {
        String body = om.writeValueAsString(createModel);
        InputStream content = doPostRequest(body, createVersionClaimUrl()).getEntity().getContent();
        return om.readValue(content, RestCommitModel.class);
    }

    public RepoResponseModel createRepo(RepoCreateModel repoCreateModel) throws IOException, UnsuccessfulRequestException {
        String body = om.writeValueAsString(repoCreateModel);
        InputStream content = doPostRequest(body, configuration.getProviderBaseUrl() + "/api/repo").getEntity().getContent();
        return om.readValue(content, RepoResponseModel.class);
    }

    protected HttpResponse doGetRequest(String url) throws IOException, UnsuccessfulRequestException {
        HttpGet httpGet = new HttpGet(url);

        HttpResponse response = client.execute(httpGet);

        if(response.getStatusLine().getStatusCode() != 200) {
            String content = EntityUtils.toString(response.getEntity());
            logger.error("Server response was not 200 (was {}): message {}", response.getStatusLine().getStatusCode(), content);
            throw new UnsuccessfulRequestException(response.getStatusLine().getStatusCode(), content);
        }

        return response;
    }

    protected HttpResponse doPostRequest(String body, String url) throws UnsuccessfulRequestException, IOException {
        HttpEntity entity = new ByteArrayEntity(body.getBytes(), ContentType.APPLICATION_JSON);

        HttpPost post = new HttpPost(url);
        post.setEntity(entity);

        HttpResponse response = client.execute(post);

        if(response.getStatusLine().getStatusCode() != 200) {
            String content = EntityUtils.toString(response.getEntity());
            logger.error("Server response was not 200 (was {}): message {}", response.getStatusLine().getStatusCode(), content);
            throw new UnsuccessfulRequestException(response.getStatusLine().getStatusCode(), content);
        }

        return response;
    }

    @NotNull
    private String createVersionSearchUrl() {
        return configuration.getProviderBaseUrl() + "/api/version/" + configuration.getRepoId() + "/search";
    }

    @NotNull
    private String createVersionClaimUrl() {
        return configuration.getProviderBaseUrl() + "/api/version/" + configuration.getRepoId();
    }

    @NotNull
    private String createVerionFindUrl(String commitId) {
        return configuration.getProviderBaseUrl() + "/api/version/" + configuration.getRepoId() + "/" + commitId;
    }

}
