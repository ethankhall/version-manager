package io.ehdev.conrad.database.api;

import io.ehdev.conrad.model.user.*;

public interface TokenManagementApi {
    ConradGeneratedToken createToken(ConradUser user, ConradTokenType type);

    boolean isTokenValid(ConradToken token);

    void invalidateTokenValid(ConradToken token);

    ConradUser findUser(ConradToken token);
}
