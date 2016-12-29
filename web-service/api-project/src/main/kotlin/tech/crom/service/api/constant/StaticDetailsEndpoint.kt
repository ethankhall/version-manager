package tech.crom.service.api.constant

import tech.crom.rest.model.constant.BumperListReponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tech.crom.business.api.VersionBumperApi

@Service
@RequestMapping("/api/v1/constant")
open class StaticDetailsEndpoint(
    val versionBumperApi: VersionBumperApi
) {

    @RequestMapping("/bumpers", method = arrayOf(RequestMethod.GET))
    open fun getVersionBumpers(): ResponseEntity<BumperListReponse> {
        val list = versionBumperApi.findAll().map { BumperListReponse.Bumper(it.bumperName, it.description) }

        return ResponseEntity.ok(BumperListReponse(list))
    }
}
