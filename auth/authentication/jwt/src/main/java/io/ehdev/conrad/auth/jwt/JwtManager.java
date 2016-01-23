package io.ehdev.conrad.auth.jwt;

import io.ehdev.conrad.model.user.ConradToken;
import io.ehdev.conrad.model.user.ConradTokenType;
import io.ehdev.conrad.model.user.ConradUser;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface JwtManager {

    String createUserToken(ConradUser user);

    String createToken(ConradUser user, ConradTokenType type);

    @NotNull
    Optional<ConradUser> findUser(String token);

    @NotNull
    Optional<ConradToken> findToken(String token);
}
