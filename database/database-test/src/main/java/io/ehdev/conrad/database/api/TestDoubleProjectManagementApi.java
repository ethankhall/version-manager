package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.project.ApiProjectModel;
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDoubleProjectManagementApi implements ProjectManagementApi {

    private final Map<String, ApiProjectModel> projects = new HashMap<>();

    @Override
    public ApiProjectModel createProject(String projectName) {
        projects.put(projectName, new ApiProjectModel("foo", new ArrayList<>()));
        return projects.get(projectName);
    }

    @Override
    public List<ApiVersionBumperModel> findAllVersionBumpers(String projectName) {
        return null;
    }
}
