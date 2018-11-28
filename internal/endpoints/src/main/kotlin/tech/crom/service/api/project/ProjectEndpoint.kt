package tech.crom.service.api.project

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import tech.crom.business.api.ProjectApi
import tech.crom.rest.model.project.FindProjectResponse

@Controller
@RequestMapping(value = ["/api/v1/projects"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ProjectEndpoint @Autowired constructor(
    val projectApi: ProjectApi
) {

    @RequestMapping(method = [RequestMethod.GET])
    fun findProjects(@RequestParam("offset", defaultValue = "0") offset: String,
                     @RequestParam("size", defaultValue = "50") size: String): ResponseEntity<FindProjectResponse> {
        val offsetInt = offset.toInt()
        val sizeInt = size.toInt()

        val projects = projectApi.findProjects(offsetInt, sizeInt)
        val projectDetails = projects.projects.map { FindProjectResponse.ProjectDetails(it.projectName) }

        return ResponseEntity.ok(FindProjectResponse(projectDetails, projects.totalSize))
    }

}
