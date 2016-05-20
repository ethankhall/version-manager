package io.ehdev.conrad.database.model.repo.details;

import java.util.UUID;

public class ResourceId {

    private final UUID id;
    private final String name;

    public ResourceId(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public ResourceId(String name) {
        this(name, null);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean exists() {
        return id != null;
    }
}