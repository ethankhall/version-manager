package io.ehdev.conrad.client.java.internal

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.lib.RepositoryBuilder
import org.eclipse.jgit.revwalk.RevWalk
import java.io.File
import java.io.IOException
import java.util.*

class GitHelper(val searchDir: File) {

    private val repo: Repository
    private val git: Git

    init {
        repo = RepositoryBuilder().findGitDir(searchDir).build()
        git = Git(repo)
    }

    @Throws(IOException::class, GitAPIException::class)
    fun findCommitsFromHead(): List<String> {
        val commits = git.log().setMaxCount(50).call()
        val commitIds = LinkedList<String>()
        for (commit in commits) {
            commitIds.add(commit.id.name)
        }

        return commitIds
    }

    val headCommitId: ObjectId
        @Throws(IOException::class)
        get() {
            val head = repo.getRef(Constants.HEAD)
            return head.objectId
        }

    val headCommitMessage: String
        @Throws(IOException::class)
        get() {
            val walk = RevWalk(repo)
            return walk.parseCommit(headCommitId).fullMessage
        }

    @Throws(GitAPIException::class)
    fun tag(version: String) {
        git.tag().setName(String.format("v%s", version)).call()
    }
}
