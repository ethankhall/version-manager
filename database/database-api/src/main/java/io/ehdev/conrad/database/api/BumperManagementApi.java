package io.ehdev.conrad.database.api;

import io.ehdev.conrad.model.internal.ApiVersionBumper;

import java.util.Optional;

public interface BumperManagementApi {
    Optional<ApiVersionBumper> findVersionBumperByName(String name);
}
