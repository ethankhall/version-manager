package io.ehdev.conrad.service.api.util;

import io.ehdev.conrad.database.model.project.commit.ApiFullCommitModel;
import io.ehdev.conrad.database.model.project.ApiProjectModel;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.model.rest.RestCommitModel;
import io.ehdev.conrad.model.rest.RestProjectModel;
import io.ehdev.conrad.model.rest.RestRepoDetailsModel;
import io.ehdev.conrad.model.rest.RestRepoModel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConversionUtility {

    public static RestProjectModel toRestModel(ApiProjectModel project) {
        List<RestRepoModel> repoModels = Collections.emptyList();
        if(project.getRepos() != null && !project.getRepos().isEmpty()) {
            repoModels = project.getRepos().stream().map(ConversionUtility::toRestModel).collect(Collectors.toList());
        }
        return new RestProjectModel(project.getName(), repoModels);
    }

    public static RestCommitModel toRestModel(ApiFullCommitModel commit) {
        return new RestCommitModel(commit.getCommitId(), commit.getVersion());
    }

    public static RestRepoModel toRestModel(ApiRepoModel repo) {
        return new RestRepoModel(repo.getId(), repo.getName(), repo.getUrl(), repo.getBumperName(), repo.getProjectName());
    }

    public static RestRepoDetailsModel toRestModel(ApiRepoDetailsModel details) {
        return new RestRepoDetailsModel(toRestModel(details.getRepo()));
    }
}
