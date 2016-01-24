package io.ehdev.conrad.database.api;

import io.ehdev.conrad.model.internal.ApiProject;
import io.ehdev.conrad.model.internal.ApiVersionBumper;

import java.util.List;

public interface ProjectManagementApi {

    ApiProject createProject(String projectName);

    List<ApiVersionBumper> findAllVersionBumpers(String projectName);
}
