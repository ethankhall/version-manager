package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.model.project.ApiProjectModel;
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;

import java.util.List;

public interface ProjectManagementApi {

    ApiProjectModel createProject(String projectName) throws ProjectAlreadyExistsException;

    List<ApiVersionBumperModel> findAllVersionBumpers(String projectName);
}
