package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.ProjectManagementApi;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.impl.bumper.VersionBumperModelRepository;
import io.ehdev.conrad.database.impl.project.ProjectModel;
import io.ehdev.conrad.database.impl.project.ProjectModelRepository;
import io.ehdev.conrad.model.internal.ApiProject;
import io.ehdev.conrad.model.internal.ApiVersionBumper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultProjectManagementApi implements ProjectManagementApi {

    private final ProjectModelRepository projectModelRepository;
    private final VersionBumperModelRepository versionBumperModelRepository;

    @Autowired
    public DefaultProjectManagementApi(ProjectModelRepository projectModelRepository,
                                       VersionBumperModelRepository versionBumperModelRepository) {
        this.projectModelRepository = projectModelRepository;
        this.versionBumperModelRepository = versionBumperModelRepository;
    }

    @Override
    public ApiProject createProject(String projectName) {
        return ModelConversionUtility.toApiModel(projectModelRepository.save(new ProjectModel(projectName)));
    }

    public List<ApiVersionBumper> findAllVersionBumpers(String projectName) {
        return versionBumperModelRepository.findAvailableBumpers(projectName);
    }
}
