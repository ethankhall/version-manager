package tech.crom.service.api.repo.version.state

import io.ehdev.conrad.service.api.exception.BaseHttpException
import io.ehdev.conrad.service.api.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tech.crom.business.api.CommitApi
import tech.crom.business.exception.CommitNotFoundException
import tech.crom.model.commit.CommitIdContainer
import tech.crom.rest.model.version.UpdateVersionState
import tech.crom.web.api.model.RequestDetails

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}/version/{version:.+}/state")
open class RepoVersionStateEndpoint(
    val commitApi: CommitApi
) {
    @RequestMapping(method = arrayOf(RequestMethod.PUT), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun migrateState(requestDetails: RequestDetails,
                     @PathVariable("version") versionArg: String,
                     @RequestBody body: UpdateVersionState): ResponseEntity<Any> {

        try {
            commitApi.updateState(requestDetails.cromRepo!!, CommitIdContainer(versionArg), body.nextState)
            return ResponseEntity(HttpStatus.OK)
        } catch (e: CommitNotFoundException) {
            throw CommitNotFound("Commit ${e.commitId} was not found")
        }
    }

    class CommitNotFound(e: String) : BaseHttpException(HttpStatus.NOT_FOUND, ErrorCode.VERSION_NOT_FOUND, e)
}
