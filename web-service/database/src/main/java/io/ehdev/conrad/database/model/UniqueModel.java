package io.ehdev.conrad.database.model;

import java.util.UUID;

public interface UniqueModel {
    UUID getId();

    void setId(UUID uuid);
}
