package io.ehdev.conrad.database.model.permission;

import java.util.UUID;

public interface ApiTokenAuthentication {
    UUID getUuid();
    String getNiceName();
}
