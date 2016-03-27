package io.ehdev.conrad.client.java.internal

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ehdev.conrad.client.java.*
import io.ehdev.conrad.model.commit.CommitIdCollection
import io.ehdev.conrad.model.repository.CreateRepoRequest
import io.ehdev.conrad.model.repository.CreateRepoResponse
import io.ehdev.conrad.model.version.CreateVersionRequest
import okhttp3.OkHttpClient
import org.eclipse.jgit.api.errors.GitAPIException
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.File
import java.io.IOException
import java.net.URISyntaxException

class DefaultVersionManagerClient(val client: OkHttpClient, val configuration: VersionServiceConfiguration) : VersionManagerClient {

    private val conradClient: ConradClient

    init {
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(configuration.applicationUrl)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .client(client)
            .build();

        conradClient = ConradClient(retrofit.create(ProjectConradClient::class.java), retrofit.create(RepositoryConradClient::class.java))
    }

    @Throws(IOException::class, GitAPIException::class, URISyntaxException::class)
    override fun findFinalVersion(searchDir: File): Version {
        val gitHelper = GitHelper(searchDir)
        return Version(
            conradClient.findVersion(
                configuration.authToken!!,
                configuration.projectName!!,
                configuration.repoName!!,
                gitHelper.headCommitId.name).execute().body().version)
    }

    @Throws(IOException::class, GitAPIException::class, URISyntaxException::class)
    override fun findVersion(searchDir: File): Version {
        val gitHelper = GitHelper(searchDir)
        val commitIds = gitHelper.findCommitsFromHead()
        return findVersion(commitIds)
    }

    @Throws(URISyntaxException::class, IOException::class)
    override fun findVersion(commitIds: List<String>): Version {
        return Version(
            conradClient.findVersion(
                configuration.authToken!!,
                configuration.projectName!!,
                configuration.repoName!!,
                CommitIdCollection(commitIds)).execute().body().version)
    }

    @Throws(IOException::class)
    override fun claimVersion(commitIds: List<String>, message: String, headCommitId: String): Version {
        val newVersion = conradClient.createNewVersion(
            configuration.authToken!!,
            configuration.projectName!!,
            configuration.repoName!!,
            CreateVersionRequest(commitIds, message, headCommitId)).execute().body()

        return Version(newVersion.version)
    }

    @Throws(IOException::class, GitAPIException::class)
    override fun claimVersion(rootProjectDir: File): Version {
        val gitHelper = GitHelper(rootProjectDir)
        val commitIds = gitHelper.findCommitsFromHead()
        return claimVersion(commitIds, gitHelper.headCommitMessage, gitHelper.headCommitId.name)
    }

    @Throws(IOException::class)
    override fun createRepository(repoCreateModel: CreateRepoRequest): CreateRepoResponse {
        return conradClient.createRepository(
            configuration.authToken!!,
            configuration.projectName!!,
            configuration.repoName!!,
            repoCreateModel).execute().body()
    }

    @Throws(IOException::class, GitAPIException::class)
    override fun tagVersion(rootProjectDir: File, version: String) {
        GitHelper(rootProjectDir).tag(version)
    }
}
