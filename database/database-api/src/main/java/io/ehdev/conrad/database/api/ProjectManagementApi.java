package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.model.project.ApiProject;
import io.ehdev.conrad.model.internal.ApiVersionBumper;

import java.util.List;

public interface ProjectManagementApi {

    ApiProject createProject(String projectName) throws ProjectAlreadyExistsException;

    List<ApiVersionBumper> findAllVersionBumpers(String projectName);
}
