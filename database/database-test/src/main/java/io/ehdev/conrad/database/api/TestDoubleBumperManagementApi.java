package io.ehdev.conrad.database.api;

import io.ehdev.conrad.model.internal.ApiVersionBumper;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class TestDoubleBumperManagementApi implements BumperManagementApi {

    private final HashMap<String, ApiVersionBumper> map = new HashMap<>();

    public TestDoubleBumperManagementApi addBumper(String name, String className) {
        map.put(name, new ApiVersionBumper(UUID.randomUUID(), className, "some desc", name));
        return this;
    }

    @Override
    public Optional<ApiVersionBumper> findVersionBumperByName(String name) {
        return Optional.ofNullable(map.get(name));
    }
}
