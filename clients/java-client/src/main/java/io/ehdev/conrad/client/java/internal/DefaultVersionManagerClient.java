package io.ehdev.conrad.client.java.internal;

import io.ehdev.conrad.client.java.Version;
import io.ehdev.conrad.client.java.VersionManagerClient;
import io.ehdev.conrad.client.java.VersionServiceConfiguration;
import io.ehdev.conrad.client.java.exception.UnsuccessfulRequestException;
import io.ehdev.conrad.model.repo.RepoCreateModel;
import io.ehdev.conrad.model.repo.RepoResponseModel;
import io.ehdev.conrad.model.version.UncommitedVersionModel;
import io.ehdev.conrad.model.version.VersionCommitModel;
import io.ehdev.conrad.model.version.VersionCreateModel;
import io.ehdev.conrad.model.version.VersionSearchModel;
import org.apache.http.client.HttpClient;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class DefaultVersionManagerClient implements VersionManagerClient {

    final HttpHelper httpHelper;
    final VersionServiceConfiguration configuration;

    public DefaultVersionManagerClient(HttpClient client, VersionServiceConfiguration configuration) {
        this.httpHelper = new HttpHelper(client, configuration);
        this.configuration = configuration;
    }

    public Version findFinalVersion(File searchDir) throws IOException, GitAPIException, URISyntaxException, UnsuccessfulRequestException {
        GitHelper gitHelper = new GitHelper(searchDir);
        return new Version(httpHelper.findFinalVersion(gitHelper.getHeadCommitId().getName()).getVersion());
    }

    @Override
    public Version findVersion(File searchDir) throws IOException, GitAPIException, URISyntaxException, UnsuccessfulRequestException {
        GitHelper gitHelper = new GitHelper(searchDir);
        List<String> commitIds = gitHelper.findCommitsFromHead();
        return findVersion(commitIds);
    }

    @Override
    public Version findVersion(List<String> commitIds) throws URISyntaxException, IOException, UnsuccessfulRequestException {
        VersionSearchModel searchModel = new VersionSearchModel(commitIds);
        UncommitedVersionModel version = httpHelper.findVersion(searchModel);
        return new Version(version.getVersion());
    }

    @Override
    public Version claimVersion(List<String> commitIds, String message, String headCommitId) throws IOException, UnsuccessfulRequestException {
        VersionCreateModel versionCreateModel = new VersionCreateModel(commitIds, message, headCommitId, configuration.getToken());
        VersionCommitModel commitedVersionModel = httpHelper.claimVersion(versionCreateModel);
        return new Version(commitedVersionModel.getVersion());
    }

    @Override
    public Version claimVersion(File rootProjectDir) throws IOException, GitAPIException, UnsuccessfulRequestException {
        GitHelper gitHelper = new GitHelper(rootProjectDir);
        List<String> commitIds = gitHelper.findCommitsFromHead();
        return claimVersion(commitIds, gitHelper.getHeadCommitMessage(), gitHelper.getHeadCommitId().getName());
    }

    @Override
    public RepoResponseModel createRepository(RepoCreateModel repoCreateModel) throws IOException, UnsuccessfulRequestException {
        return httpHelper.createRepo(repoCreateModel);
    }
}
