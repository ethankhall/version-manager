package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.database.model.user.ApiToken;

public interface TokenManagementApi {
    ApiGeneratedUserToken createToken(ApiTokenAuthentication authentication);

    ApiGeneratedUserToken createToken(String projectName, String repoName);

    boolean isTokenValid(ApiToken token);

    void invalidateTokenValid(ApiToken token);

    ApiTokenAuthentication findUser(ApiToken token);
}
