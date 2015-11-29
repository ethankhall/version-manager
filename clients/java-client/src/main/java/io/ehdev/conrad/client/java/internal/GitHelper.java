package io.ehdev.conrad.client.java.internal;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GitHelper {

    private final Repository repo;
    private final Git git;

    public GitHelper(File searchDir) throws IOException {
        repo = new BaseRepositoryBuilder().findGitDir(searchDir).build();
        git = new Git(repo);
    }

    public List<String> findCommitsFromHead() throws IOException, GitAPIException {
        ObjectId resolve = repo.resolve("remotes/origin/master~20");
        Iterable<RevCommit> commits = git.log().addRange(resolve, repo.resolve(Constants.HEAD)).call();
        List<String> commitIds = new LinkedList<String>();
        for (RevCommit commit : commits) {
            commitIds.add(commit.getId().getName());
        }
        commitIds.add(resolve.getName());

        return commitIds;
    }

    public ObjectId getHeadCommitId() throws IOException {
        Ref head = repo.getRef(Constants.HEAD);
        return head.getObjectId();
    }

    public String getHeadCommitMessage() throws IOException {
        RevWalk walk = new RevWalk(repo);
        return walk.parseCommit(getHeadCommitId()).getFullMessage();
    }

    public void tag(String version) throws GitAPIException {
        git.tag().setName(String.format("v%s", version)).call();
    }
}
