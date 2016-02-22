package io.ehdev.conrad.authentication.jwt;

import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.database.model.user.ApiToken;
import org.apache.commons.lang3.tuple.Pair;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface JwtManager {

    String createToken(ApiTokenAuthentication user);

    String createToken(ApiGeneratedUserToken token);

    @NotNull
    Optional<Pair<ApiTokenAuthentication, ApiToken>> parseToken(String token);
}
