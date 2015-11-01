package io.ehdev.conrad.client.java;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ehdev.conrad.model.version.UncommitedVersionModel;
import io.ehdev.conrad.model.version.VersionSearchModel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class HttpRequester {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequester.class);
    private static final ObjectMapper om = new ObjectMapper();

    final HttpClient client;
    final VersionServiceConfiguration configuration;

    public HttpRequester(HttpClient client, VersionServiceConfiguration configuration) {
        this.client = client;
        this.configuration = configuration;
    }

    public Version getVersion(File searchDir) throws IOException, GitAPIException, URISyntaxException, UnsuccessfulRequestException {
        Repository repo = new BaseRepositoryBuilder().findGitDir(searchDir).build();
        Git git = new Git(repo);
        ObjectId resolve = repo.resolve("remotes/origin/master~20");
        Iterable<RevCommit> commits = git.log().addRange(resolve, repo.resolve(Constants.HEAD)).call();
        List<String> commitIds = new LinkedList<String>();
        for (RevCommit commit : commits) {
            commitIds.add(commit.getId().getName());
        }
        commitIds.add(resolve.getName());

        return getVersion(commitIds);
    }

    public Version getVersion(List<String> commitIds) throws URISyntaxException, IOException, UnsuccessfulRequestException {
        String body = om.writeValueAsString(new VersionSearchModel(commitIds));

        HttpEntity entity = new ByteArrayEntity(body.getBytes(), ContentType.APPLICATION_JSON);

        HttpPost post = new HttpPost(createSearchUrl());
        post.setEntity(entity);

        HttpResponse response = client.execute(post);
        String content = EntityUtils.toString(response.getEntity());

        if(response.getStatusLine().getStatusCode() != 200) {
            logger.error("Server response was not 200 (was {}): message {}", response.getStatusLine().getStatusCode(), content);
            throw new UnsuccessfulRequestException();
        }

        UncommitedVersionModel repoVersionModel = om.readValue(content, UncommitedVersionModel.class);
        return new Version(repoVersionModel.getVersion());
    }

    @NotNull
    private String createSearchUrl() {
        return configuration.getProviderBaseUrl() + "/api/version/" + configuration.getRepoId() + "/search";
    }

    public class UnsuccessfulRequestException extends Exception {
        public UnsuccessfulRequestException() {
            super("Server communication failed.");
        }
    }
}
