package io.ehdev.conrad.version.bumper.api;

import io.ehdev.conrad.version.bumper.VersionBumper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class DefaultVersionBumperService implements VersionBumperService {

    private final Map<String, VersionBumper> versionBumpers = new HashMap<>();

    @Autowired
    public DefaultVersionBumperService(final Set<VersionBumper> bumperSet) {
        bumperSet.forEach(bumper -> versionBumpers.put(bumper.getClass().getName(), bumper));
    }

    public VersionBumper findVersionBumper(String className) {
        return Optional
            .ofNullable(versionBumpers.get(className))
            .orElseThrow(() -> new RuntimeException("Unknown bumper " + className));
    }
}
