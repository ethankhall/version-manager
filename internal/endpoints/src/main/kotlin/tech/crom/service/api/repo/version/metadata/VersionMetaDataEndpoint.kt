package tech.crom.service.api.repo.version.metadata

import io.ehdev.conrad.service.api.aop.annotation.RepoRequired
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired
import io.ehdev.conrad.service.api.exception.BaseHttpException
import io.ehdev.conrad.service.api.exception.ErrorCode
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tech.crom.business.api.CommitApi
import tech.crom.business.api.StorageApi
import tech.crom.model.commit.CommitFilter
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.metadata.StorageData
import tech.crom.rest.model.metadata.AvailableFilesResponse
import tech.crom.web.api.model.RequestDetails


@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}/version/{version}/metadata")
class VersionMetaDataEndpoint(
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
    @ResponseBody
    @RequestMapping("/{fileName:.+}", method = [RequestMethod.GET])
    fun retrieveFile(requestDetails: RequestDetails,
                     @PathVariable("version") versionArg: String,
                     @PathVariable("fileName") fileName: String): Mono<ServerResponse> {
        val commit = getVersionOrThrow(requestDetails, versionArg)
        val file = storageApi.getFile(commit, fileName) ?: throw FileNotFoundException(fileName)

        return ServerResponse.ok()
            .contentType(MediaType.parseMediaType(file.contentType))
            .contentLength(file.bytes.size.toLong())
            .body(Flux.from { DefaultDataBufferFactory().wrap(file.bytes) }, DataBuffer::class.java)
    }

    @RepoRequired
    @WritePermissionRequired
    @RequestMapping("/{fileName:.+}", method = [RequestMethod.POST])
    fun uploadFile(requestDetails: RequestDetails,
                   @PathVariable("version") versionArg: String,
                   @PathVariable("fileName") fileName: String,
                   @RequestParam("file") file: MultipartFile): ResponseEntity<Any> {
        val commit = getVersionOrThrow(requestDetails, versionArg)

        try {
            storageApi.insertFile(requestDetails.cromRepo!!, commit, StorageData(fileName, file.bytes, file.contentType))
        } catch (e: StorageApi.MaxStorageReachedException) {
            throw StorageLimitExceeded(e.message)
        } catch (e: StorageApi.BackedException) {
            throw UnknownStorageError(e.message ?: "An unknown storage error occured.")
        }

        return ResponseEntity(HttpStatus.CREATED)
    }

    private fun getVersionOrThrow(requestDetails: RequestDetails, version: String): PersistedCommit {
        val commitFilter = CommitFilter(listOf(CommitIdContainer(version)))
        return commitApi.findCommit(requestDetails.cromRepo!!, commitFilter) ?: throw VersionNotFoundException(version)
    }

    class UnknownStorageError(e: String) : BaseHttpException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.UNKNOWN_STORAGE_BACKEND_ERROR, e)
    class StorageLimitExceeded(e: String?) : BaseHttpException(HttpStatus.NOT_ACCEPTABLE, ErrorCode.PROJECT_STORAGE_LIMIT_EXCEEDED, e)

    class VersionNotFoundException(version: String) :
        BaseHttpException(HttpStatus.NOT_FOUND, ErrorCode.VERSION_NOT_FOUND, "Count not find $version.")

    class FileNotFoundException(name: String) : BaseHttpException(HttpStatus.NOT_FOUND, ErrorCode.METADATA_NOT_FOUND, "$name was not found.")
}
