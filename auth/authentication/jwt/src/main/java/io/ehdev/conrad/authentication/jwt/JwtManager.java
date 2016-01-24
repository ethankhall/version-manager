package io.ehdev.conrad.authentication.jwt;

import io.ehdev.conrad.model.user.ConradToken;
import io.ehdev.conrad.model.user.ConradTokenType;
import io.ehdev.conrad.model.user.ConradUser;
import org.apache.commons.lang3.tuple.Pair;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface JwtManager {

    String createUserToken(ConradUser user);

    String createToken(ConradUser user, ConradTokenType type);

    @NotNull
    Optional<Pair<ConradUser, ConradToken>> parseToken(String token);
}
