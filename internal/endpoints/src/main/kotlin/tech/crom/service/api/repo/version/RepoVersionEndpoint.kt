package tech.crom.service.api.repo.version

import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.util.UriComponentsBuilder
import tech.crom.business.api.CommitApi
import tech.crom.model.commit.CommitFilter
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.impl.RequestedCommit
import tech.crom.rest.model.version.CreateVersionRequest
import tech.crom.rest.model.version.CreateVersionResponse
import tech.crom.rest.model.version.GetAllVersionsResponse
import tech.crom.rest.model.version.GetVersionResponse
import tech.crom.service.api.ReverseApiCommitComparator
import tech.crom.web.api.model.RequestDetails
import javax.transaction.Transactional

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}")
class RepoVersionEndpoint @Autowired
constructor(private val commitApi: CommitApi) {

    @RepoRequired
    @ReadPermissionRequired
    @RequestMapping(value = ["/versions"], method = [RequestMethod.GET])
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
    @RequestMapping(value = ["/version"], method = [RequestMethod.POST])
    fun createNewVersion(requestDetails: RequestDetails,
                         @RequestBody versionModel: CreateVersionRequest,
                         request: UriComponentsBuilder): ResponseEntity<CreateVersionResponse> {
        val commits = versionModel.commits.map { CommitIdContainer(it) }.toList()

        val commitModel = RequestedCommit(versionModel.commitId, versionModel.message, null)
        val nextCommit = commitApi.createCommit(requestDetails.cromRepo!!, commitModel, commits)

        val uri = request.path(nextCommit.getVersionString()).build().toUri()

        val response = CreateVersionResponse(versionModel.commitId,
            nextCommit.getVersionString(),
            nextCommit.state,
            nextCommit.createdAt)
        return ResponseEntity.created(uri).body(response)
    }

    @RepoRequired
    @ReadPermissionRequired
    @RequestMapping(value = ["/version/{versionArg:.+}"], method = [RequestMethod.GET])
    fun findVersion(requestDetails: RequestDetails,
                    @PathVariable("versionArg") versionArg: String,
                    @RequestParam("filter", required = false) filter: String?): ResponseEntity<Any> {
        val commitFilter = CommitFilter(listOf(CommitIdContainer(versionArg)), filter)
        val commit = commitApi.findCommit(requestDetails.cromRepo!!, commitFilter) ?:
            return ResponseEntity(HttpStatus.NOT_FOUND)

        val versionResponse = GetVersionResponse(commit.commitId,
            commit.getVersionString(),
            commit.state,
            commit.createdAt)
        return ResponseEntity.ok(versionResponse)
    }
}
