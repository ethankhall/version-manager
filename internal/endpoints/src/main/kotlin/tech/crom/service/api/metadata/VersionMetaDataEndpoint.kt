package tech.crom.service.api.metadata

import io.ehdev.conrad.service.api.aop.annotation.RepoRequired
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired
import io.ehdev.conrad.service.api.exception.BaseHttpException
import io.ehdev.conrad.service.api.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tech.crom.business.api.CommitApi
import tech.crom.business.api.StorageApi
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.metadata.StorageData
import tech.crom.rest.model.metadata.AvailableFilesResponse
import tech.crom.web.api.model.RequestDetails
import javax.servlet.http.HttpServletResponse

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}/version/{version}/metadata")
open class VersionMetaDataEndpoint(
    val commitApi: CommitApi,
    val storageApi: StorageApi
) {

    @RepoRequired
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    open fun getFiles(requestDetails: RequestDetails,
                      @PathVariable("version") versionArg: String): ResponseEntity<AvailableFilesResponse> {
        val commit = getVersionOrThrow(requestDetails, versionArg)
        val files = storageApi.listFilesForVersion(commit)
        return ResponseEntity.ok(AvailableFilesResponse(files))
    }

    @RepoRequired
    @ResponseBody
    @RequestMapping("/{fileName:.+}", method = arrayOf(RequestMethod.GET))
    open fun retrieveFile(requestDetails: RequestDetails,
                          @PathVariable("version") versionArg: String,
                          @PathVariable("fileName") fileName: String,
                          response: HttpServletResponse) {
        val commit = getVersionOrThrow(requestDetails, versionArg)
        val file = storageApi.getFile(commit, fileName) ?: throw FileNotFoundException(fileName)

        response.contentType = file.contentType
        response.setContentLength(file.bytes.size)
        response.status = HttpStatus.OK.value()

        file.bytes.inputStream().copyTo(response.outputStream)
        response.outputStream.flush()
    }

    @RepoRequired
    @WritePermissionRequired
    @RequestMapping("/{fileName:.+}", method = arrayOf(RequestMethod.POST))
    open fun uploadFile(requestDetails: RequestDetails,
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
        val idContainer = CommitIdContainer(version)
        return commitApi.findCommit(requestDetails.cromRepo!!, idContainer) ?: throw VersionNotFoundException(version)
    }

    class UnknownStorageError(e: String) : BaseHttpException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.UNKOWN_STORAGE_BACKEND_ERROR, e)
    class StorageLimitExceeded(e: String?) : BaseHttpException(HttpStatus.NOT_ACCEPTABLE, ErrorCode.PROJECT_STORAGE_LIMIT_EXCEEDED, e)

    class VersionNotFoundException(version: String) :
        BaseHttpException(HttpStatus.NOT_FOUND, ErrorCode.VERSION_NOT_FOUND, "Count not find $version.")

    class FileNotFoundException(name: String): BaseHttpException(HttpStatus.NOT_FOUND, ErrorCode.METADATA_NOT_FOUND, "$name was not found.")
}
