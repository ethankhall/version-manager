package tech.crom.service.api.metadata

import io.ehdev.conrad.model.metadata.AvailableFilesResponse
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired
import io.ehdev.conrad.service.api.exception.BaseHttpException
import io.ehdev.conrad.service.api.exception.ErrorCode
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import tech.crom.business.api.CommitApi
import tech.crom.business.api.StorageApi
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.metadata.StorageData
import tech.crom.web.api.model.RequestDetails

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}/version/{version}/metadata")
open class VersionMetaDataEndpoint(
    val commitApi: CommitApi,
    val storageApi: StorageApi
) {

    @RepoRequired
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun getFiles(requestDetails: RequestDetails,
                 @PathVariable("version") versionArg: String): ResponseEntity<AvailableFilesResponse> {
        val commit = getVersionOrThrow(requestDetails, versionArg)
        val files = storageApi.listFilesForVersion(commit)
        return ResponseEntity.ok(AvailableFilesResponse(files))
    }

    @RepoRequired
    @RequestMapping("/{filename:.+}", method = arrayOf(RequestMethod.GET))
    fun retrieveFile(requestDetails: RequestDetails,
                     @PathVariable("version") versionArg: String,
                     @PathVariable("fileName") fileName: String): ResponseEntity<ByteArray> {
        val commit = getVersionOrThrow(requestDetails, versionArg)
        val file = storageApi.getFile(commit, fileName) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.parseMediaType(file.contentType)
        httpHeaders.contentLength = file.bytes.size.toLong()

        return ResponseEntity (file.bytes, httpHeaders, HttpStatus.OK)
    }

    @RepoRequired
    @WritePermissionRequired
    @RequestMapping("/{filename:.+}", method = arrayOf(RequestMethod.POST))
    fun uploadFile(requestDetails: RequestDetails,
                   @PathVariable("version") versionArg: String,
                   @PathVariable("fileName") fileName: String,
                   @RequestParam("file") file: MultipartFile): ResponseEntity<Any> {
        val commit = getVersionOrThrow(requestDetails, versionArg)

        try {
            storageApi.insertFile(requestDetails.cromRepo!!, commit, StorageData(fileName, file.bytes, file.contentType))
        } catch (e: StorageApi.MaxStorageReachedException) {
            throw StorageLimitExceeded(e.message)
        }

        return ResponseEntity(HttpStatus.CREATED)
    }

    private fun getVersionOrThrow(requestDetails: RequestDetails, version: String): PersistedCommit {
        val idContainer = CommitIdContainer(version)
        return commitApi.findCommit(requestDetails.cromRepo!!, idContainer) ?: throw VersionNotFoundException(version)
    }

    class StorageLimitExceeded(e: String?) : BaseHttpException(HttpStatus.NOT_ACCEPTABLE, ErrorCode.PROJECT_STORAGE_LIMIT_EXCEEDED, e)

    class VersionNotFoundException(version: String) :
        BaseHttpException(HttpStatus.NOT_FOUND, ErrorCode.VERSION_NOT_FOUND, "Count not find $version.")
}
