package io.ehdev.conrad.database.internal;

import java.util.UUID;

public interface UniqueModel {
    UUID getId();

    void setId(UUID uuid);
}
