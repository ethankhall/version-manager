package io.ehdev.conrad.database.impl;

import java.util.UUID;

public interface UniqueModel {
    UUID getId();

    void setId(UUID uuid);
}
