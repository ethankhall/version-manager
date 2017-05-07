package tech.crom.service.api.repo.version

import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tech.crom.business.api.CommitApi
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.impl.RequestedCommit
import tech.crom.rest.model.version.CreateVersionRequest
import tech.crom.rest.model.version.CreateVersionResponse
import tech.crom.rest.model.version.GetAllVersionsResponse
import tech.crom.rest.model.version.GetVersionResponse
import tech.crom.service.api.ReverseApiCommitComparator
import tech.crom.web.api.model.RequestDetails
import java.net.URI
import javax.servlet.http.HttpServletRequest
import javax.transaction.Transactional

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}")
open class RepoVersionEndpoint @Autowired
constructor(private val commitApi: CommitApi) {

    @RepoRequired
    @ReadPermissionRequired
    @ApiOperation(value = "Get all versions from repo")
    @RequestMapping(value = "/versions", method = arrayOf(RequestMethod.GET))
    fun getAllVersions(requestDetails: RequestDetails): ResponseEntity<GetAllVersionsResponse> {
        val response = GetAllVersionsResponse()

        commitApi.findAllCommits(requestDetails.cromRepo!!)
            .stream()
            .sorted(ReverseApiCommitComparator())
            .forEach { it ->
                val commit = GetAllVersionsResponse.CommitModel(it.commitId,
                    it.getVersionString(),
                    it.state,
                    it.createdAt)
                response.addCommit(commit)
            }

        if (!response.commits.isEmpty()) {
            response.latest = response.commits[0]
        }

        return ResponseEntity.ok(response)
    }

    @RepoRequired
    @Transactional
    @WritePermissionRequired
    @ApiOperation(value = "Adds a new version to repo. Will return with next version", tags = arrayOf("write-user"))
    @RequestMapping(value = "/version", method = arrayOf(RequestMethod.POST))
    fun createNewVersion(requestDetails: RequestDetails,
                         @RequestBody versionModel: CreateVersionRequest,
                         request: HttpServletRequest): ResponseEntity<CreateVersionResponse> {
        val commits = versionModel.commits.map { CommitIdContainer(it) }.toList()

        val commitModel = RequestedCommit(versionModel.commitId, versionModel.message, null)
        val nextCommit = commitApi.createCommit(requestDetails.cromRepo!!, commitModel, commits)

        val uri = URI.create(request.requestURL.toString() + "/" + nextCommit.getVersionString())

        val response = CreateVersionResponse(versionModel.commitId,
            nextCommit.getVersionString(),
            nextCommit.state,
            nextCommit.createdAt)
        return ResponseEntity.created(uri).body(response)
    }

    @RepoRequired
    @ReadPermissionRequired
    @ApiOperation(value = "Get a version, can be version, commit id, or 'latest'")
    @RequestMapping(value = "/version/{versionArg:.+}", method = arrayOf(RequestMethod.GET))
    fun findVersion(requestDetails: RequestDetails,
                    @PathVariable("versionArg") versionArg: String): ResponseEntity<GetVersionResponse> {
        val commit = commitApi.findCommit(requestDetails.cromRepo!!, CommitIdContainer(versionArg)) ?:
            return ResponseEntity(HttpStatus.NOT_FOUND)

        val versionResponse = GetVersionResponse(commit.commitId,
            commit.getVersionString(),
            commit.state,
            commit.createdAt)
        return ResponseEntity.ok(versionResponse)
    }
}
