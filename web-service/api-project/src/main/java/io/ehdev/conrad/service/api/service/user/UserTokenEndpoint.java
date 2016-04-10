package io.ehdev.conrad.service.api.service.user;

import io.ehdev.conrad.authentication.jwt.JwtManager;
import io.ehdev.conrad.database.api.TokenManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.model.permission.CreateTokenResponse;
import io.ehdev.conrad.model.permission.GetTokensResponse;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.swagger.annotations.*;
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
@Api
@RequestMapping("/api/v1/user/tokens")
public class UserTokenEndpoint {
    private final TokenManagementApi tokenManagementApi;
    private final JwtManager jwtManager;

    @Autowired
    public UserTokenEndpoint(TokenManagementApi tokenManagementApi, JwtManager jwtManager) {
        this.tokenManagementApi = tokenManagementApi;
        this.jwtManager = jwtManager;
    }

    @LoggedInUserRequired
    @RequestMapping(value = "/{tokenId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteToken(ApiParameterContainer repoModel, @PathVariable("tokenId") String tokenId) {
        tokenManagementApi.invalidateTokenValidByJoinId(UUID.fromString(tokenId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoggedInUserRequired
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateTokenResponse> createNewToken(ApiParameterContainer repoModel) {
        ApiGeneratedUserToken token = tokenManagementApi.createToken(repoModel.getUser());
        String authToken = jwtManager.createToken(token);

        CreateTokenResponse created = new CreateTokenResponse(
            token.getUuid(),
            token.getCreatedAt(),
            token.getExpiresAt(),
            authToken);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @LoggedInUserRequired
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetTokensResponse> findAllTokens(ApiParameterContainer repoModel) {
        List<GetTokensResponse.TokenEntryModel> tokens = tokenManagementApi
            .getTokens(repoModel.getUser())
            .stream()
            .map(it -> new GetTokensResponse.TokenEntryModel(it.getId(), it.getCreatedAt(), it.getExpiresAt()))
            .collect(Collectors.toList());

        return new ResponseEntity<>(new GetTokensResponse(tokens), HttpStatus.OK);
    }
}
