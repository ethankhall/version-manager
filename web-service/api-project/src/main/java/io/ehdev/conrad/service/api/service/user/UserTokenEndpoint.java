package io.ehdev.conrad.service.api.service.user;

import io.ehdev.conrad.model.permission.CreateTokenResponse;
import io.ehdev.conrad.model.permission.GetTokensResponse;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
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

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/user/tokens")
public class UserTokenEndpoint {

    private final TokenManagementApi tokenManagementApi;

    @Autowired
    public UserTokenEndpoint(TokenManagementApi tokenManagementApi) {
        this.tokenManagementApi = tokenManagementApi;
    }

    @LoggedInUserRequired
    @RequestMapping(value = "/{tokenId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteToken(RequestDetails requestDetails, @PathVariable("tokenId") String tokenId) {
        tokenManagementApi.invalidateToken(UUID.fromString(tokenId), TokenType.USER);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoggedInUserRequired
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateTokenResponse> createNewToken(RequestDetails requestDetails,
                                                              @RequestParam(value = "validFor", required = false) Integer validFor) {

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime expiresAt = validFor == null ? now.plusDays(60) : now.plusDays(validFor);
        GeneratedTokenDetails token = tokenManagementApi.createToken(requestDetails.getRequestPermission().getCromUser(), expiresAt);

        CreateTokenResponse created = new CreateTokenResponse(
            token.getId(),
            token.getCreatedAt(),
            token.getExpiresAt(),
            token.getValue());

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @LoggedInUserRequired
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetTokensResponse> findAllTokens(RequestDetails requestDetails) {
        List<GetTokensResponse.TokenEntryModel> tokens = tokenManagementApi
            .getTokens(requestDetails.getRequestPermission().getCromUser())
            .stream()
            .map(it -> new GetTokensResponse.TokenEntryModel(it.getId(), it.getCreatedAt(), it.getExpiresAt()))
            .collect(Collectors.toList());

        return new ResponseEntity<>(new GetTokensResponse(tokens), HttpStatus.OK);
    }
}
