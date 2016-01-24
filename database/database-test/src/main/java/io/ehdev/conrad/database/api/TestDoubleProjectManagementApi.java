package io.ehdev.conrad.database.api;

import io.ehdev.conrad.model.internal.ApiVersionBumper;
import io.ehdev.conrad.model.project.ApiProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDoubleProjectManagementApi implements ProjectManagementApi {

    private final Map<String, ApiProject> projects = new HashMap<>();

    public Map<String, ApiProject> getProjects() {
        return projects;
    }

    @Override
    public ApiProject createProject(String projectName) {
        projects.put(projectName, new ApiProject(projectName, new ArrayList<>()));
        return projects.get(projectName);
    }

    @Override
    public List<ApiVersionBumper> findAllVersionBumpers(String projectName) {
        return null;
    }
}
