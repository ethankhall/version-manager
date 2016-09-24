package tech.crom.service.api.metadata

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import tech.crom.business.api.StorageApi
import tech.crom.web.api.model.RequestDetails

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}/metadata")
open class VersionMetaDataEndpoint(
    val storageApi: StorageApi
) {

    @RequestMapping("/{fileName}", method = arrayOf(RequestMethod.POST))
    fun uploadFile(container: RequestDetails,
                   @PathVariable("fileName") fileName: String,
                   @RequestParam("file") file: MultipartFile) {
        val storedFile = storageApi.insertFile(container.cromRepo!!, file.bytes, file.contentType, fileName)
    }
}
