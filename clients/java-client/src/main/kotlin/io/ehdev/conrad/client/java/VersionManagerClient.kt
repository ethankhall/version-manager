package io.ehdev.conrad.client.java

import io.ehdev.conrad.model.repository.CreateRepoRequest
import io.ehdev.conrad.model.repository.CreateRepoResponse
import org.eclipse.jgit.api.errors.GitAPIException
import java.io.File
import java.io.IOException
import java.net.URISyntaxException

interface VersionManagerClient {

    @Throws(IOException::class, GitAPIException::class, URISyntaxException::class)
    fun findFinalVersion(searchDir: File): Version

    @Throws(IOException::class, GitAPIException::class, URISyntaxException::class)
    fun findVersion(searchDir: File): Version

    @Throws(URISyntaxException::class, IOException::class)
    fun findVersion(commitIds: List<String>): Version

    @Throws(IOException::class)
    fun claimVersion(commitIds: List<String>, message: String, headCommitId: String): Version

    @Throws(IOException::class, GitAPIException::class)
    fun claimVersion(rootProjectDir: File): Version

    @Throws(IOException::class)
    fun createRepository(repoCreateModel: CreateRepoRequest): CreateRepoResponse

    @Throws(IOException::class, GitAPIException::class)
    fun tagVersion(rootProjectDir: File, version: String)
}
