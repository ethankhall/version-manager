package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.model.rest.RestRepoDetailsModel;
import io.ehdev.conrad.model.user.ConradUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static io.ehdev.conrad.service.api.util.ConversionUtility.toRestModel;

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}")
public class RepoDetailsEndpoint {
    private final RepoManagementApi repoManagementApi;

    @Autowired
    public RepoDetailsEndpoint(RepoManagementApi repoManagementApi) {
        this.repoManagementApi = repoManagementApi;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ResponseEntity<RestRepoDetailsModel> getRepoDetails(ApiRepoModel repoModel,
                                                               @Valid @NotNull ConradUser user) {
        return ResponseEntity.ok(toRestModel(repoManagementApi.getDetails(repoModel).get()));
    }
}
