package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.project.ApiProjectDetails;
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;

import java.util.List;

public interface ProjectManagementApi {

    void createProject(ApiParameterContainer apiParameterContainer) throws ProjectAlreadyExistsException;

    List<ApiVersionBumperModel> findAllVersionBumpers(String projectName);

    ApiProjectDetails getProjectDetails(ApiParameterContainer apiParameterContainer);
}
