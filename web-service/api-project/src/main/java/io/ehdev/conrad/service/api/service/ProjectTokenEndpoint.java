package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.authentication.jwt.JwtManager;
import io.ehdev.conrad.database.api.TokenManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.service.model.LinkUtilities;
import io.ehdev.conrad.service.api.service.model.token.CreateTokenResponse;
import io.ehdev.conrad.service.api.service.model.token.GetTokenList;
import io.ehdev.conrad.service.api.service.model.token.GetTokenListEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/project/{projectName}/token")
public class ProjectTokenEndpoint {

    private final TokenManagementApi tokenManagementApi;
    private final JwtManager jwtManager;

    @Autowired
    public ProjectTokenEndpoint(TokenManagementApi tokenManagementApi, JwtManager jwtManager) {
        this.tokenManagementApi = tokenManagementApi;
        this.jwtManager = jwtManager;
    }

    @LoggedInUserRequired
    @AdminPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/{tokenId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteToken(ApiParameterContainer repoModel,
                                      @PathVariable("tokenId") String tokenId) {
        tokenManagementApi.invalidateTokenValidByJoinId(UUID.fromString(tokenId));
        return new ResponseEntity(HttpStatus.OK);
    }

    @LoggedInUserRequired
    @AdminPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateTokenResponse> createNewToken(ApiParameterContainer repoModel) {
        ApiGeneratedUserToken token = tokenManagementApi.createToken(repoModel.getProjectName(), null);
        String authToken = jwtManager.createToken(token);
        CreateTokenResponse created = new CreateTokenResponse(token.getUuid(), token.getCreatedAt(), token.getExpiresAt(), authToken);
        created.add(LinkUtilities.projectLink(repoModel, "project"));

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @LoggedInUserRequired
    @AdminPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetTokenList> findAllTokens(ApiParameterContainer repoModel) {
        List<GetTokenListEntry> tokens = tokenManagementApi
            .getTokens(repoModel.getProjectName(), null)
            .stream()
            .map(it -> new GetTokenListEntry(it.getId(), it.getCreatedAt(), it.getExpiresAt()))
            .collect(Collectors.toList());

        return new ResponseEntity<>(new GetTokenList(tokens), HttpStatus.OK);
    }
}