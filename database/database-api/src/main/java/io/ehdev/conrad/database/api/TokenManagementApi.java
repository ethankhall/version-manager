package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.database.model.user.ApiToken;
import io.ehdev.conrad.database.model.user.ApiTokenType;
import io.ehdev.conrad.database.model.user.ApiUser;

public interface TokenManagementApi {
    ApiGeneratedUserToken createToken(ApiUser user, ApiTokenType type);

    boolean isTokenValid(ApiToken token);

    void invalidateTokenValid(ApiToken token);

    ApiUser findUser(ApiToken token);
}
