package io.ehdev.conrad.database.api;


import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;

import java.util.Optional;

public interface BumperManagementApi {
    Optional<ApiVersionBumperModel> findVersionBumperByName(String name);
}
