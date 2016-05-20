package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.model.repo.details.AuthUserDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.database.model.project.ApiProjectDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceId;

import java.util.Optional;


public interface ProjectManagementApi {

    ResourceId createProject(AuthUserDetails authUserDetails, ResourceDetails resourceDetails) throws ProjectAlreadyExistsException;

    Optional<ApiProjectDetails> getProjectDetails(ResourceDetails resourceDetails);
}
