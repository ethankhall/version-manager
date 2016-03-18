package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.project.ApiProjectDetails;
import io.ehdev.conrad.database.model.project.ApiProjectModel;
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class TestDoubleProjectManagementApi implements ProjectManagementApi {

    private final Map<String, ApiProjectModel> projects = new HashMap<>();

    @Override
    public void createProject(ApiParameterContainer apiParameterContainer) {
        String projectName = apiParameterContainer.getProjectName();
        projects.put(projectName, new ApiProjectModel(projectName, new ArrayList<>()));
    }

    @Override
    public List<ApiVersionBumperModel> findAllVersionBumpers(String projectName) {
        return null;
    }

    @Override
    public Optional<ApiProjectDetails> getProjectDetails(ApiParameterContainer apiParameterContainer) {
        return Optional.empty();
    }
}
