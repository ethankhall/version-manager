package io.ehdev.conrad.authentication.jwt;

import io.ehdev.conrad.database.model.user.ApiToken;
import io.ehdev.conrad.database.model.user.ApiTokenType;
import io.ehdev.conrad.database.model.user.ApiUser;
import org.apache.commons.lang3.tuple.Pair;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface JwtManager {

    String createUserToken(ApiUser user);

    String createToken(ApiUser user, ApiTokenType type);

    @NotNull
    Optional<Pair<ApiUser, ApiToken>> parseToken(String token);
}
