package io.ehdev.conrad.client.java;

import io.ehdev.conrad.client.java.exception.UnsuccessfulRequestException;
import io.ehdev.conrad.model.repo.RepoCreateModel;
import io.ehdev.conrad.model.repo.RepoResponseModel;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface VersionManagerClient {

    Version findFinalVersion(File searchDir) throws IOException, GitAPIException, URISyntaxException, UnsuccessfulRequestException;

    Version findVersion(File searchDir) throws IOException, GitAPIException, URISyntaxException, UnsuccessfulRequestException;

    Version findVersion(List<String> commitIds) throws URISyntaxException, IOException, UnsuccessfulRequestException;

    Version claimVersion(List<String> commitIds, String message, String headCommitId) throws IOException, UnsuccessfulRequestException;

    Version claimVersion(File rootProjectDir) throws IOException, GitAPIException, UnsuccessfulRequestException;

    RepoResponseModel createRepository(RepoCreateModel repoCreateModel) throws IOException, UnsuccessfulRequestException;
}
