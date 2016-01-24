package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.BumperManagementApi;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.impl.bumper.VersionBumperModelRepository;
import io.ehdev.conrad.model.internal.ApiVersionBumper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultBumperManagementApi implements BumperManagementApi {

    private final VersionBumperModelRepository bumperModelRepository;

    @Autowired
    public DefaultBumperManagementApi(VersionBumperModelRepository bumperModelRepository) {
        this.bumperModelRepository = bumperModelRepository;
    }

    @Override
    public Optional<ApiVersionBumper> findVersionBumperByName(String name) {
        return Optional.ofNullable(bumperModelRepository.findByBumperName(name)).map(ModelConversionUtility::toApiModel);
    }
}
