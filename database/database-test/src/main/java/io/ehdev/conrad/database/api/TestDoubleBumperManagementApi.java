package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class TestDoubleBumperManagementApi implements BumperManagementApi {

    private final HashMap<String, ApiVersionBumperModel> map = new HashMap<>();

    public TestDoubleBumperManagementApi addBumper(String name, String className) {
        map.put(name, new ApiVersionBumperModel(UUID.randomUUID(), className, "some desc", name));
        return this;
    }

    @Override
    public Optional<ApiVersionBumperModel> findVersionBumperByName(String name) {
        return Optional.ofNullable(map.get(name));
    }
}
