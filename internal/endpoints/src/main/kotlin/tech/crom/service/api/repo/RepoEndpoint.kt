package tech.crom.service.api.repo

import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tech.crom.business.api.CommitApi
import tech.crom.business.api.PermissionApi
import tech.crom.business.api.RepositoryApi
import tech.crom.business.api.VersionBumperApi
import tech.crom.model.commit.CommitFilter
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.impl.RequestedCommit
import tech.crom.rest.model.commit.CommitIdCollection
import tech.crom.rest.model.permission.PermissionGrant
import tech.crom.rest.model.repository.CreateRepoRequest
import tech.crom.rest.model.repository.CreateRepoResponse
import tech.crom.rest.model.repository.GetRepoResponse
import tech.crom.rest.model.version.VersionSearchResponse
import tech.crom.web.api.model.RequestDetails
import javax.transaction.Transactional

@Service
@RequestMapping(value = "/api/v1/project/{projectName}/repo/{repoName}", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
open class RepoEndpoint @Autowired
constructor(private val repositoryApi: RepositoryApi,
            private val permissionApi: PermissionApi,
            private val versionBumperApi: VersionBumperApi,
            private val commitApi: CommitApi) {

    @RepoRequired
    @Transactional
    @AdminPermissionRequired
    @RequestMapping(method = arrayOf(RequestMethod.DELETE))
    @ApiOperation(value = "Deletes an existing repository", tags = arrayOf("admin-user"))
    open fun deleteRepo(requestDetails: RequestDetails): ResponseEntity<Any> {
        repositoryApi.deleteRepo(requestDetails.cromRepo!!)
        return ResponseEntity(HttpStatus.OK)
    }

    @Transactional
    @WritePermissionRequired
    @RepoRequired(exists = false)
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    @ApiOperation(value = "Creates a new repository", tags = arrayOf("write-user"))
    open fun createRepo(requestDetails: RequestDetails,
                        @RequestBody createModel: CreateRepoRequest): ResponseEntity<CreateRepoResponse> {
        val repoName = requestDetails.rawRequest.getRepoName()
        val versionBumper = versionBumperApi.findVersionBumper(createModel.bumperName)
        val repo = repositoryApi.createRepo(requestDetails.cromProject!!,
            repoName!!,
            versionBumper,
            createModel.repoUrl,
            createModel.description,
            true)

        if (createModel.history != null) {
            val requestedCommits = createModel.history!!
                .map { RequestedCommit(it.commitId, "[set version " + it.version + "]", it.createdAt) }
                .toList()

            var commitIdContainer: CommitIdContainer? = null
            for (requestedCommit in requestedCommits) {
                val commitList = if (commitIdContainer == null) emptyList<CommitIdContainer>() else listOf<CommitIdContainer>(commitIdContainer)
                val (_, commitId) = commitApi.createCommit(repo, requestedCommit, commitList)
                commitIdContainer = CommitIdContainer(commitId)
            }
        }

        val model = CreateRepoResponse(
            requestDetails.cromProject!!.projectName,
            repo.repoName,
            createModel.repoUrl)

        return ResponseEntity(model, HttpStatus.CREATED)
    }

    @RepoRequired
    @ReadPermissionRequired
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    @ApiOperation(value = "Get an existing repository")
    open fun getRepoDetails(requestDetails: RequestDetails): ResponseEntity<GetRepoResponse> {
        val (cromRepo, _, _, checkoutUrl) = repositoryApi.getRepoDetails(requestDetails.cromRepo!!)

        val restRepoModel = GetRepoResponse(
            requestDetails.cromProject!!.projectName,
            requestDetails.cromRepo!!.repoName,
            checkoutUrl
        )

        permissionApi.getPermissions(cromRepo).forEach { (cromUser, cromPermission) ->
            restRepoModel.addPermission(
                PermissionGrant(cromUser.userName,
                    PermissionGrant.AccessLevel.valueOf(cromPermission.name)))
        }

        return ResponseEntity.ok(restRepoModel)
    }

    @RepoRequired
    @ReadPermissionRequired
    @ApiOperation(value = "Finds the latest commit from a list of ids")
    @RequestMapping(value = "/search/version", method = arrayOf(RequestMethod.POST))
    open fun searchForVersionInHistory(requestDetails: RequestDetails,
                                       @RequestBody versionModel: CommitIdCollection): ResponseEntity<VersionSearchResponse> {
        val commits = versionModel.commits.map { CommitIdContainer(it) }.toList()
        val latestCommit = commitApi.findLatestCommit(requestDetails.cromRepo!!, CommitFilter(commits))

        if (latestCommit == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        } else {
            val body = VersionSearchResponse(latestCommit.commitId,
                latestCommit.version.versionString,
                latestCommit.state,
                latestCommit.createdAt)
            return ResponseEntity.ok(body)
        }
    }
}
