package io.ehdev.conrad.service.api.service.repo;

import io.ehdev.conrad.model.permission.CreateTokenResponse;
import io.ehdev.conrad.model.permission.GetTokensResponse;
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tech.crom.business.api.TokenManagementApi;
import tech.crom.model.token.GeneratedTokenDetails;
import tech.crom.model.token.TokenType;
import tech.crom.web.api.model.RequestDetails;

import javax.transaction.Transactional;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}/token")
public class RepoTokenEndpoint {

    private final TokenManagementApi tokenManagementApi;

    @Autowired
    public RepoTokenEndpoint(TokenManagementApi tokenManagementApi) {
        this.tokenManagementApi = tokenManagementApi;
    }

    @Transactional
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RepoRequired
    @RequestMapping(value = "/{tokenId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteToken(RequestDetails requestDetails,
                                      @PathVariable("tokenId") String tokenId) {
        tokenManagementApi.invalidateToken(tokenId, TokenType.REPOSITORY);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RepoRequired
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateTokenResponse> createNewToken(RequestDetails requestDetails,
                                                              @RequestParam(value = "validFor", required = false) Integer validFor) {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime expiresAt = validFor == null ? now.plusDays(60) : now.plusDays(validFor);
        GeneratedTokenDetails token = tokenManagementApi.createToken(requestDetails.getCromRepo(), expiresAt);

        CreateTokenResponse created = new CreateTokenResponse(
            token.getId(),
            token.getCreatedAt(),
            token.getExpiresAt(),
            token.getValue());

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @RepoRequired
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetTokensResponse> findAllTokens(RequestDetails requestDetails) {
        List<GetTokensResponse.TokenEntryModel> tokens = tokenManagementApi
            .getTokens(requestDetails.getCromRepo())
            .stream()
            .map(it -> new GetTokensResponse.TokenEntryModel(it.getId(), it.getCreatedAt(), it.getExpiresAt()))
            .collect(Collectors.toList());

        return new ResponseEntity<>(new GetTokensResponse(tokens), HttpStatus.OK);
    }
}
