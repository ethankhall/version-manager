package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.ProjectManagementApi;
import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.impl.bumper.VersionBumperModelRepository;
import io.ehdev.conrad.database.impl.project.ProjectModel;
import io.ehdev.conrad.database.impl.project.ProjectModelRepository;
import io.ehdev.conrad.database.model.project.ApiProjectModel;
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public ApiProjectModel createProject(String projectName) throws ProjectAlreadyExistsException {
        if(projectModelRepository.findByProjectName(projectName) != null) {
            throw new ProjectAlreadyExistsException(projectName);
        }
        return ModelConversionUtility.toApiModel(projectModelRepository.save(new ProjectModel(projectName)));
    }

    public List<ApiVersionBumperModel> findAllVersionBumpers(String projectName) {
        return versionBumperModelRepository
            .findAvailableBumpers(projectName)
            .stream()
            .map(ModelConversionUtility::toApiModel)
            .collect(Collectors.toList());
    }
}
