package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.repo.details.AuthUserDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.token.RetrievedToken;
import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.database.model.user.ApiToken;

import java.util.List;
import java.util.UUID;

public interface TokenManagementApi {

    ApiGeneratedUserToken createToken(ApiTokenAuthentication authentication);

    ApiGeneratedUserToken createToken(AuthUserDetails authentication);

    ApiGeneratedUserToken createToken(ResourceDetails resourceDetails);

    List<RetrievedToken> getTokens(ResourceDetails resourceDetails);

    List<RetrievedToken> getTokens(AuthUserDetails authentication);

    boolean isTokenValid(ApiToken token);

    void invalidateTokenValidByJoinId(UUID tokenId);

    void invalidateTokenValid(ApiToken token);

    ApiTokenAuthentication findUser(ApiToken token);
}
