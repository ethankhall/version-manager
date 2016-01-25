package io.ehdev.conrad.version.bumper.api;

import io.ehdev.conrad.version.bumper.VersionBumper;

public interface VersionBumperService {
    VersionBumper findVersionBumper(String className);
}
