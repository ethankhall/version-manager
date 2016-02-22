package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.TokenManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.database.model.user.ApiProvidedToken;
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.service.model.LinkUtilities;
import io.ehdev.conrad.service.api.service.model.token.CreateTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}/token")
public class RepoTokenEndpoint {

    private final TokenManagementApi tokenManagementApi;

    @Autowired
    public RepoTokenEndpoint(TokenManagementApi tokenManagementApi) {
        this.tokenManagementApi = tokenManagementApi;
    }

    @AdminPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/{tokenId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteToken(ApiParameterContainer repoModel,
                                      @PathVariable("tokenId") String tokenId) {
        tokenManagementApi.invalidateTokenValid(new ApiProvidedToken(UUID.fromString(tokenId)));
        return new ResponseEntity(HttpStatus.OK);
    }

    @AdminPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateTokenResponse> createNewToken(ApiParameterContainer repoModel) {
        ApiGeneratedUserToken token = tokenManagementApi.createToken(repoModel.getProjectName(), repoModel.getRepoName());
        CreateTokenResponse created = new CreateTokenResponse(token.getUuid(), token.getCreatedAt(), token.getExpiresAt());
        created.add(LinkUtilities.repositoryLink(repoModel, LinkUtilities.REPOSITORY_REF));

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
