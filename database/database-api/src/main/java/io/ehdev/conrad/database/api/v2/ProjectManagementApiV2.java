package io.ehdev.conrad.database.api.v2;

import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.api.v2.details.AuthUserDetails;
import io.ehdev.conrad.database.api.v2.details.ResourceDetails;
import io.ehdev.conrad.database.model.project.ApiProjectDetails;

import java.util.Optional;


public interface ProjectManagementApiV2 {

    void createProject(AuthUserDetails authUserDetails, ResourceDetails resourceDetails) throws ProjectAlreadyExistsException;

    Optional<ApiProjectDetails> getProjectDetails(ResourceDetails resourceDetails);
}
