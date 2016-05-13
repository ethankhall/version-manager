package io.ehdev.conrad.database.api.v2;

import io.ehdev.conrad.database.api.v2.details.ResourceDetails;
import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.token.RetrievedToken;
import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.database.model.user.ApiToken;

import java.util.List;
import java.util.UUID;

public interface TokenManagementApiV2 {

    ApiGeneratedUserToken createToken(ApiTokenAuthentication authentication);

    ApiGeneratedUserToken createToken(ResourceDetails resourceDetails);

    List<RetrievedToken> getTokens(ResourceDetails resourceDetails);

    List<RetrievedToken> getTokens(ApiTokenAuthentication authentication);

    boolean isTokenValid(ApiToken token);

    void invalidateTokenValidByJoinId(UUID tokenId);

    void invalidateTokenValid(ApiToken token);

    ApiTokenAuthentication findUser(ApiToken token);
}
